package com.ecospend.paybybank.app.module.frPayment

import android.app.Activity
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentGetResponse
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

/**
 * FrPayment (Standing Order) API
 * A Standing Order is an instruction that an account holder gives to their bank to make payments of a fixed amount at regular intervals.
 * Payments are made automatically by the bank on a defined schedule (e.g. weekly or monthly) on an ongoing basis, unless a specified condition has been met, such as an end-date being reached or a set number of payments having been made.
 * Standing Orders can only be created, amended or cancelled by the account holder, typically by using their online or telephone banking service. They are most commonly used for recurring payments where the amount stays the same, such as rent payments, subscription services or regular account top-ups.
 */
class FrPayment() {
    /**
     *  Opens webview using with `url` of Frplink
     *
     *@property activity: Activty that provides to present bank selection
     *@property frpUrl:  Url of fr payment.
     *@property redirectUrl:  Redirect url of fr payment.
     *@property completion: It provides to handle result or error
     */
    fun openWithUrl(
        activity: Activity,
        uniqueID: String,
        frpUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = FrPaymentExecuteType.OpenWithUrl(uniqueID, frpUrl, redirectUrl),
            completion = completion
        )
    }

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: FrPaymentExecuteType,
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
        type: FrPaymentExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {

            is FrPaymentExecuteType.OpenWithUrl -> {
                withContext(Dispatchers.IO) {
                    FrPaymentGetResponse(uniqueID = type.uniqueID, url = type.url, redirectURL = type.redirectUrl)
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
