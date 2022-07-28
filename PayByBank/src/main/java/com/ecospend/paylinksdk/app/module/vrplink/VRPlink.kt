package com.ecospend.paybybank.app.module.vrplink

import android.app.Activity
import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.model.paylink.request.IamTokenRequest
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkCreateRequest
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkDeleteRequest
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkGetRecordsRequest
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkGetRequest
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkCreateResponse
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkGetRecordsResponse
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkGetResponse
import com.ecospend.paybybank.data.repository.IamRepository
import com.ecospend.paybybank.data.repository.VRPlinkRepository
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

class VRPlink(
    private val iamRepository: IamRepository,
    private val vrPlinkRepository: VRPlinkRepository
) {

    fun initiate(
        activity: Activity,
        request: VRPlinkCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = VRPlinkExecuteType.Initiate(request),
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
            type = VRPlinkExecuteType.Open(uniqueID),
            completion = completion
        )
    }

    fun createVRPlink(
        request: VRPlinkCreateRequest,
        completion: (VRPlinkCreateResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = vrPlinkRepository.createVRPlink(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun getVRPlink(
        request: VRPlinkGetRequest,
        completion: (VRPlinkGetResponse?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = vrPlinkRepository.getVRPlink(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun deactivateVRPlink(
        request: VRPlinkDeleteRequest,
        completion: (Boolean?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = vrPlinkRepository.deleteVRPlink(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    fun getVRPlinkRecords(
        request: VRPlinkGetRecordsRequest,
        completion: (List<VRPlinkGetRecordsResponse>?, PayByBankError?) -> Unit
    ) {
        Coroutine.coroutineScope.launch(Dispatchers.IO) {
            val response = vrPlinkRepository.getVRPlinkRecords(request)
            completion.invoke(response, null)
        }
        Coroutine.cancel()
    }

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: VRPlinkExecuteType,
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
        type: VRPlinkExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {
            is VRPlinkExecuteType.Open -> {
                withContext(Dispatchers.IO) {
                    vrPlinkRepository.getVRPlink(
                        VRPlinkGetRequest(type.id)
                    )
                }
            }
            is VRPlinkExecuteType.Initiate -> {
                val response = vrPlinkRepository.createVRPlink(type.request)
                response?.uniqueID ?: run {
                    completion(null, PayByBankError.WrongPaylink("id Error."))
                    return null
                }
                withContext(Dispatchers.IO) {
                    vrPlinkRepository.getVRPlink(
                        VRPlinkGetRequest(response.uniqueID)
                    )
                }
            }
        }

        val id = response?.uniqueID
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
