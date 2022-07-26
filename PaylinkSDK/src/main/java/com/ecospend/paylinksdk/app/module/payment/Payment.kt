package com.ecospend.paylinksdk.app.module.payment

import android.app.Activity
import com.ecospend.paylinksdk.app.PayByBankState
import com.ecospend.paylinksdk.data.remote.model.paylink.request.IamTokenRequest
import com.ecospend.paylinksdk.data.remote.model.payment.request.PaymentCheckURLRequest
import com.ecospend.paylinksdk.data.remote.model.payment.request.PaymentCreateRefundRequest
import com.ecospend.paylinksdk.data.remote.model.payment.request.PaymentCreateRequest
import com.ecospend.paylinksdk.data.remote.model.payment.request.PaymentGetRequest
import com.ecospend.paylinksdk.data.remote.model.payment.request.PaymentListRequest
import com.ecospend.paylinksdk.data.remote.model.payment.response.PaymentCheckURLResponse
import com.ecospend.paylinksdk.data.remote.model.payment.response.PaymentCreateResponse
import com.ecospend.paylinksdk.data.remote.model.payment.response.PaymentGetResponse
import com.ecospend.paylinksdk.data.remote.model.payment.response.PaymentListResponse
import com.ecospend.paylinksdk.data.repository.IamRepository
import com.ecospend.paylinksdk.data.repository.PaymentRepository
import com.ecospend.paylinksdk.shared.coroutine.Coroutine
import com.ecospend.paylinksdk.shared.coroutine.cancel
import com.ecospend.paylinksdk.shared.logger.AppLog
import com.ecospend.paylinksdk.shared.model.completion.PayByBankError
import com.ecospend.paylinksdk.shared.model.completion.PayByBankResult
import com.ecospend.paylinksdk.shared.network.interceptor.ErrorInterceptor
import com.ecospend.paylinksdk.ui.pay.AppWebView
import com.ecospend.paylinksdk.ui.pay.model.AppWebViewUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Payment(
    private val iamRepository: IamRepository,
    private val paymentRepository: PaymentRepository
) {

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
        val clienID = PayByBankState.Config.clientID
            ?: run {
                completion(null, PayByBankError.NotConfigured)
                return false
            }
        val clientSecret = PayByBankState.Config.clientSecret
            ?: run {
                completion(null, PayByBankError.NotConfigured)
                return false
            }
        return withContext(Dispatchers.IO) {
            val response = iamRepository.connect(
                IamTokenRequest(
                    clientID = clienID,
                    clientSecret = clientSecret
                )
            )
            return@withContext if (response?.accessToken.isNullOrBlank()) {
                completion(null, PayByBankError.WrongPaylink("token error."))
                false
            } else true
        }
    }

    //endregion
}
