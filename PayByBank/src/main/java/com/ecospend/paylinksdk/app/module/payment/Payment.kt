package com.ecospend.paybybank.app.module.payment

import android.app.Activity
import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentCheckURLRequest
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentCreateRefundRequest
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentGetRequest
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentListRequest
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentCheckURLResponse
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentGetResponse
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentListResponse
import com.ecospend.paybybank.data.repository.IamRepository
import com.ecospend.paybybank.data.repository.PaymentRepository
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

/**
 * Payment API
 * Note: Domestic instant payments, international payments, and scheduled payments are all accomplished from the same /payments endpoint.
 * The payment type is automatically identified by our system depending whether the debtor and creditor accounts are from different countries (for international payments),
 * or whether a value has been set for the scheduled_for parameter (meaning a scheduled payment).
 */
class Payment(
    private val iamRepository: IamRepository,
    private val paymentRepository: PaymentRepository
) {

    /**
     *  Opens bank application or bank website using with request model of payment
     *
     *@property activity: Activty that provides to present bank selection
     *@property request: Request to create payment
     *@property completion: It provides to handle result or error
     */
    fun initiate(
        activity: Activity,
        request: PaymentCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = PaymentExecuteType.Initiate(request),
            completion = completion
        )
    }

    /**
     * Opens bank application or bank website using with `id` of payment
     *
     *@property activity: Activty that provides to present bank selection
     *@property uniqueID: Unique id value of payment.
     *@property completion: It provides to handle result or error
     */
    fun open(
        activity: Activity,
        uniqueID: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = PaymentExecuteType.Open(uniqueID),
            completion = completion
        )
    }

    /**
     *  Opens bank application or bank website using with request model of payment
     *
     *@property request: Request to create payment.
     *@property completion: It provides to handle result or error
     */
    fun createPayment(
        request: PaymentCreateRequest,
        completion: (PaymentCreateResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = paymentRepository.createPayment(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    /**
     *  Gets payments.
     *
     *@property request:  Request to list of payments with filters.
     *@property completion: It provides to handle result or error
     */
    fun listPayments(
        request: PaymentListRequest,
        completion: (PaymentListResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = paymentRepository.listPayments(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    /**
     *  Gets payment detail.
     *
     *@property request: : Request to get payment detail with id.
     *@property completion: It provides to handle result or error
     */
    fun getPayment(
        request: PaymentGetRequest,
        completion: (PaymentGetResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = paymentRepository.getPayment(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    /**
     * Checks availability of payment url.
     *
     * 'url-consumed' endpoint checks whether the bank's payment url has been visited by the PSU.
     * Return's true if the PSU has logged in to the banking system for this payment.
     * In such case either wait for the PSU to finish the journey, or create a new payment.
     *
     *@property request: Request to check availability of payment url.
     *@property completion: It provides to handle result or error
     */
    fun checkPaymentURL(
        request: PaymentCheckURLRequest,
        completion: (PaymentCheckURLResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = paymentRepository.checkPaymentURL(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    /**
     *  Creates refund for given payment
     *
     *@property request: Request to create refund for given payment.
     *@property completion: It provides to handle result or error
     */
    fun createRefund(
        request: PaymentCreateRefundRequest,
        completion: (PaymentCreateResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = paymentRepository.createRefund(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: PaymentExecuteType,
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
        type: PaymentExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {
            is PaymentExecuteType.Open -> {
                withContext(Dispatchers.IO) {
                    paymentRepository.getPayment(
                        PaymentGetRequest(type.id)
                    )
                }
            }
            is PaymentExecuteType.Initiate -> {
                val response = paymentRepository.createPayment(type.request)
                response?.id ?: run {
                    completion(null, PayByBankError.WrongPaylink("id Error."))
                    return null
                }
                withContext(Dispatchers.IO) {
                    paymentRepository.getPayment(
                        PaymentGetRequest(response.id)
                    )
                }
            }
        }

        val id = response?.id
            ?: run {
                completion(null, PayByBankError.WrongPaylink("id Error."))
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

        return Triple(id, url, redirectURL)
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
