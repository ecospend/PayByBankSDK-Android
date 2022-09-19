package com.ecospend.paybybank.app.module.bulkPayment

import android.app.Activity
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentGetResponse
import com.ecospend.paybybank.shared.coroutine.Coroutine
import com.ecospend.paybybank.shared.logger.AppLog
import com.ecospend.paybybank.shared.model.completion.PayByBankError
import com.ecospend.paybybank.shared.model.completion.PayByBankResult
import com.ecospend.paybybank.shared.network.interceptor.ErrorInterceptor
import com.ecospend.paybybank.ui.pay.AppWebView
import com.ecospend.paybybank.ui.pay.model.AppWebViewUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** Bulk Payment API
* - Note: A bulk payment is a payment created from a bulk list - so it's a payment to multiple beneficiaries from a single debit account. It will show as one debit on your bank statement. As with bulk lists, there are two types: standard domestic bulk payments and bulk Inter Account Transfers (IATs).
*/
class BulkPayment() {

    /**
     *  Opens webview using with `url` of BulkPayment
     *
     *@property activity: Activty that provides to present bank selection
     *@property bulkPaymentUrl:  Url value of bulk payment.
     *@property redirectUrl:  Redirect url of bulk payment.
     *@property completion: It provides to handle result or error
     */
    fun openWithUrl(
        activity: Activity,
        uniqueID: String,
        bulkPaymentUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = BulkPaymentExecuteType.OpenWithUrl(uniqueID, bulkPaymentUrl, redirectUrl),
            completion = completion
        )
    }
    //region Private Functions

    private fun execute(
        activity: Activity,
        type: BulkPaymentExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        if (!AppLog.isInit) AppLog.init(activity)

        Coroutine.coroutineScope.launch(Dispatchers.Main) {
            val executeTypeResult = handleExecuteType(type, completion) ?: return@launch

            val appWebView = AppWebView(
                AppWebViewUIModel(
                    uniqueID = executeTypeResult.first,
                    webViewURL = executeTypeResult.second,
                    redirectURL = executeTypeResult.third,
                    completion = { result, error ->
                        completion(result, error)
                    }
                )
            )

            ErrorInterceptor.errorHandler = { result, error ->
                completion(result, error)
                completion(
                    result,
                    error ?: PayByBankError.Unknown(
                        "${error?.localizedMessage} \n ${error?.message} \n ${error?.stackTrace} \n ${error?.cause}"
                    )
                )
                error?.let {
                    launch(Dispatchers.Main) {
                        appWebView.removeViews()
                    }
                }
            }

            appWebView.open(activity)
        }
    }

    /**
     * uniqueID, url, redirectUrl
     */
    private suspend fun handleExecuteType(
        type: BulkPaymentExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {
            is BulkPaymentExecuteType.OpenWithUrl -> {
                withContext(Dispatchers.IO) {
                    BulkPaymentGetResponse(uniqueID = type.uniqueID, url = type.url, redirectURL = type.redirectUrl)
                }
            }
        }

        val uniqueID = response?.uniqueID
            ?: run {
                completion(null, PayByBankError.WrongPaylink("uniqueID Error."))
                return null
            }
        val url = response.url
            ?: run {
                completion(null, PayByBankError.WrongPaylink("url Error."))
                return null
            }
        if (!url.contains("ecospend.com")) {
            completion(null, PayByBankError.WrongPaylink("Url must contain Ecospend services"))
        }
        val redirectURL = response.redirectURL
            ?: run {
                completion(null, PayByBankError.WrongPaylink("redirectUrl Error."))
                return null
            }

        return Triple(uniqueID, url, redirectURL)
    }

    //endregion
}
