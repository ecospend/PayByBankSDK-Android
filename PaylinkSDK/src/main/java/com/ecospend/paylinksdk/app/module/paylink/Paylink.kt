package com.ecospend.paylinksdk.app.module.paylink

import android.app.Activity
import com.ecospend.paylinksdk.app.PayByBankState
import com.ecospend.paylinksdk.data.remote.model.paylink.request.IamTokenRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkCreateRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkDeleteRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkGetRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.response.PaylinkCreateResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.response.PaylinkGetResponse
import com.ecospend.paylinksdk.data.repository.IamRepository
import com.ecospend.paylinksdk.data.repository.PaylinkRepository
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

class Paylink(
    private val iamRepository: IamRepository,
    private val paylinkRepository: PaylinkRepository
) {

    fun initiate(
        activity: Activity,
        request: PaylinkCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = PaylinkExecuteType.Initiate(request),
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
            type = PaylinkExecuteType.Open(uniqueID),
            completion = completion
        )
    }

    fun createPaylink(
        request: PaylinkCreateRequest,
        completion: (PaylinkCreateResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = paylinkRepository.createPaylink(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun getPaylink(
        request: PaylinkGetRequest,
        completion: (PaylinkGetResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = paylinkRepository.getPaylink(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun deActivatePaylink(
        request: PaylinkDeleteRequest,
        completion: (Boolean, PayByBankError?) -> Unit

    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = paylinkRepository.deletePaylink(request)
            completion.invoke(response ?: false, null)
        }
        Coroutine.cancel()
    }

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: PaylinkExecuteType,
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
        type: PaylinkExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {
            is PaylinkExecuteType.Open -> {
                withContext(Dispatchers.IO) {
                    paylinkRepository.getPaylink(
                        PaylinkGetRequest(type.paylinkID)
                    )
                }
            }
            is PaylinkExecuteType.Initiate -> {
                val paylinkCreateResponse = paylinkRepository.createPaylink(
                    type.paylinkCreateRequest
                )
                paylinkCreateResponse?.uniqueID ?: run {
                    completion(null, PayByBankError.WrongPaylink("uniqueID Error."))
                    return null
                }
                withContext(Dispatchers.IO) {
                    paylinkRepository.getPaylink(
                        PaylinkGetRequest(paylinkCreateResponse.uniqueID)
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
