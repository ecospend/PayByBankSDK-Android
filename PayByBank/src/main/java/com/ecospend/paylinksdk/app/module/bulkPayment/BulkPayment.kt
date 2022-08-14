package com.ecospend.paybybank.app.module.bulkPayment

import android.app.Activity
import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentDeleteRequest
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentGetRequest
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentGetResponse
import com.ecospend.paybybank.data.repository.BulkPaymentRepository
import com.ecospend.paybybank.data.repository.IamRepository
import com.ecospend.paybybank.shared.coroutine.Coroutine
import com.ecospend.paybybank.shared.coroutine.cancel
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
class BulkPayment(
    private val iamRepository: IamRepository,
    private val bulkPaymentRepository: BulkPaymentRepository
) {
/**  Opens webview using with `uniqueID` of the BulkPayment Paylink.
     *
     *@property activity: Activity that provides to present bank selection
     *@property uniqueID: Unique id value of BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun open(
        activity: Activity,
        uniqueID: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = BulkPaymentExecuteType.Open(uniqueID),
            completion = completion
        )
    }

    /**  Opens webview using with request model of the BulkPayment Paylink.
     *
     *@property activity: Activity that provides to present bank selection
     *@property request: Request to create BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun initiate(
        activity: Activity,
        request: BulkPaymentCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = BulkPaymentExecuteType.Initiate(request),
            completion = completion
        )
    }

    /**  Creates BulkPayment
     *
     *@property request: Request to create BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun createBulkPayment(
        request: BulkPaymentCreateRequest,
        completion: (BulkPaymentCreateResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = bulkPaymentRepository.createBulkPayment(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    /**  Gets BulkPayment detail
     *
     *@property request: Request to get detail of  BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun getBulkPayment(
        request: BulkPaymentGetRequest,
        completion: (BulkPaymentGetResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = bulkPaymentRepository.getBulkPayment(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    /**  Soft deletes the BulkPayment Paylink with given id.
     *@property request: Request to deactivate BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun deactivateBulkPayment(
        request: BulkPaymentDeleteRequest,
        completion: (Boolean?, PayByBankError?) -> Unit

    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = bulkPaymentRepository.deleteBulkPayment(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: BulkPaymentExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        if (!AppLog.isInit) AppLog.init(activity)

        Coroutine.coroutineScope.launch(Dispatchers.Main) {
            auth(completion)
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
            is BulkPaymentExecuteType.Open -> {
                withContext(Dispatchers.IO) {
                    bulkPaymentRepository.getBulkPayment(
                        BulkPaymentGetRequest(type.uniqueID)
                    )
                }
            }
            is BulkPaymentExecuteType.Initiate -> {
                val response = bulkPaymentRepository.createBulkPayment(
                    type.request
                )
                response?.uniqueID ?: run {
                    completion(null, PayByBankError.WrongPaylink("uniqueID Error."))
                    return null
                }
                withContext(Dispatchers.IO) {
                    bulkPaymentRepository.getBulkPayment(
                        BulkPaymentGetRequest(response.uniqueID)
                    )
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
        val redirectURL = response.redirectURL
            ?: run {
                completion(null, PayByBankError.WrongPaylink("redirectUrl Error."))
                return null
            }

        return Triple(uniqueID, url, redirectURL)
    }

    private suspend fun auth(
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Boolean {
        PayByBankState.Config.authentication
            ?: run {
                completion(null, PayByBankError.NotConfigured)
                return false
            }
        return withContext(Dispatchers.IO) {
            val response = iamRepository.connect()
            return@withContext if (response?.accessToken.isNullOrBlank()) {
                completion(null, PayByBankError.WrongPaylink("token error."))
                false
            } else true
        }
    }

    //endregion
}
