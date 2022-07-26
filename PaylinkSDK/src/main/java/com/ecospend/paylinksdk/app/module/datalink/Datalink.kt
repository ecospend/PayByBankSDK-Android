package com.ecospend.paylinksdk.app.module.datalink

import android.app.Activity
import com.ecospend.paylinksdk.app.PayByBankState
import com.ecospend.paylinksdk.data.remote.model.datalink.request.DatalinkCreateRequest
import com.ecospend.paylinksdk.data.remote.model.datalink.request.DatalinkDeleteRequest
import com.ecospend.paylinksdk.data.remote.model.datalink.request.DatalinkGetConsentDatalinkRequest
import com.ecospend.paylinksdk.data.remote.model.datalink.request.DatalinkGetRequest
import com.ecospend.paylinksdk.data.remote.model.datalink.response.DatalinkCreateResponse
import com.ecospend.paylinksdk.data.remote.model.datalink.response.DatalinkGetResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.request.IamTokenRequest
import com.ecospend.paylinksdk.data.repository.DatalinkRepository
import com.ecospend.paylinksdk.data.repository.IamRepository
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

class Datalink(
    private val iamRepository: IamRepository,
    private val datalinkRepository: DatalinkRepository
) {

    fun open(
        activity: Activity,
        uniqueID: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = DatalinkExecuteType.Open(uniqueID),
            completion = completion
        )
    }

    fun initiate(
        activity: Activity,
        request: DatalinkCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = DatalinkExecuteType.Initiate(request),
            completion = completion
        )
    }

    fun createDatalink(
        request: DatalinkCreateRequest,
        completion: (DatalinkCreateResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = datalinkRepository.createDatalink(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun getDatalink(
        request: DatalinkGetRequest,
        completion: (DatalinkGetResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = datalinkRepository.getDatalink(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun deleteDatalink(
        request: DatalinkDeleteRequest,
        completion: (Boolean?, PayByBankError?) -> Unit

    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = datalinkRepository.deleteDatalink(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun getDatalinkOfConsent(
        request: DatalinkGetConsentDatalinkRequest,
        completion: (DatalinkGetResponse?, PayByBankError?) -> Unit

    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = datalinkRepository.getDatalinkOfConsent(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: DatalinkExecuteType,
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
        type: DatalinkExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {
            is DatalinkExecuteType.Open -> {
                withContext(Dispatchers.IO) {
                    datalinkRepository.getDatalink(
                        DatalinkGetRequest(type.uniqueID)
                    )
                }
            }
            is DatalinkExecuteType.Initiate -> {
                val datalinkCreateResponse = datalinkRepository.createDatalink(
                    type.datalinkCreateRequest
                )
                datalinkCreateResponse?.uniqueID ?: run {
                    completion(null, PayByBankError.WrongPaylink("uniqueID Error."))
                    return null
                }
                withContext(Dispatchers.IO) {
                    datalinkRepository.getDatalink(
                        DatalinkGetRequest(datalinkCreateResponse.uniqueID)
                    )
                }
            }
        }

        val uniqueID = response?.datalink?.uniqueID
            ?: run {
                completion(null, PayByBankError.WrongPaylink("uniqueID Error."))
                return null
            }
        val url = response.datalink.url
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
        val clientID = PayByBankState.Config.clientID
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
                    clientID = clientID,
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
