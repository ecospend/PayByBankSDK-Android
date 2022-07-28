package com.ecospend.paybybank.app.module.frPayment

import android.app.Activity
import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentDeleteRequest
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentGetRequest
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentGetResponse
import com.ecospend.paybybank.data.remote.model.paylink.request.IamTokenRequest
import com.ecospend.paybybank.data.repository.FrPaymentRepository
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

class FrPayment(
    private val iamRepository: IamRepository,
    private val frPaymentRepository: FrPaymentRepository
) {

    fun initiate(
        activity: Activity,
        request: FrPaymentCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = FrPaymentExecuteType.Initiate(request),
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
            type = FrPaymentExecuteType.Open(uniqueID),
            completion = completion
        )
    }

    fun createFrPayment(
        request: FrPaymentCreateRequest,
        completion: (FrPaymentCreateResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = frPaymentRepository.createFrPayment(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun getFrPayment(
        request: FrPaymentGetRequest,
        completion: (FrPaymentGetResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = frPaymentRepository.getFrPayment(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun deactivateFrPayment(
        request: FrPaymentDeleteRequest,
        completion: (Boolean, PayByBankError?) -> Unit

    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = frPaymentRepository.deleteFrPayment(request)
            completion.invoke(response ?: false, null)
        }
        Coroutine.cancel()
    }

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: FrPaymentExecuteType,
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
        type: FrPaymentExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {
            is FrPaymentExecuteType.Open -> {
                withContext(Dispatchers.IO) {
                    frPaymentRepository.getFrPayment(
                        FrPaymentGetRequest(type.uniqueID)
                    )
                }
            }
            is FrPaymentExecuteType.Initiate -> {
                val frPaymentCreateResponse = frPaymentRepository.createFrPayment(
                    type.frPaymentCreateRequest
                )
                frPaymentCreateResponse?.uniqueID ?: run {
                    completion(null, PayByBankError.WrongPaylink("uniqueID Error."))
                    return null
                }
                withContext(Dispatchers.IO) {
                    frPaymentRepository.getFrPayment(
                        FrPaymentGetRequest(frPaymentCreateResponse.uniqueID)
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
