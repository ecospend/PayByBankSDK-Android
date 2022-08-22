package com.ecospend.paybybank.app.module.vrplink

import android.app.Activity
import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.app.module.paylink.PaylinkExecuteType
import com.ecospend.paybybank.data.remote.model.paylink.response.PaylinkGetResponse
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

/**
 * VRPlink (Variable Recurring Payments) API
 * Note: Variable Recurring Payments (VRPs) let customers safely connect authorised payments providers to their bank account so that they can make payments on the customer’s behalf,
 * in line with agreed limits. VRPs offer more control and transparency than existing alternatives, such as Direct Debit payments.
 */
class VRPlink(
    private val iamRepository: IamRepository,
    private val vrPlinkRepository: VRPlinkRepository
) {

    /**
     * Opens webview using with request model of VRPlink
     *
     *@property activity: Activty that provides to present bank selection
     *@property request: Request to create VRPlink
     *@property completion: It provides to handle result or error
     */
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

    /**
     *  Opens webview using with `uniqueID` of VRPlink
     *
     *@property activity: Activty that provides to present bank selection
     *@property uniqueID: Unique id value of VRPlink.
     *@property completion: It provides to handle result or error
     */
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

    /**
     *  Opens webview using with `url` of vrpLink
     *
     *@property activity: Activty that provides to present bank selection
     *@property vrpUrl:  Unique id value of vrpLink.
     *@property completion: It provides to handle result or error
     */
    fun openUrl(
        activity: Activity,
        vrpUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = VRPlinkExecuteType.OpenUrl(vrpUrl),
            completion = completion
        )
    }

    /**
     *  Creates VRPlink
     *
     *@property request: Request to create VRPlink
     *@property completion: It provides to handle result or error
     */
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

    /**
     *   Gets VRPlink detail
     *
     *@property request: Request to get VRPlink detail
     *@property completion: It provides to handle result or error
     */
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

    /**
     *   Soft deletes the VRPlink with given id.
     *
     *@property request: Request to deactivate VRPlink
     *@property completion: It provides to handle result or error
     */
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

    /**
     *  Returns records of VRPlink
     *
     *@property request: Request to get VRPlink records
     *@property completion: It provides to handle result or error
     */
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
            is VRPlinkExecuteType.OpenUrl -> {
                withContext(Dispatchers.IO) {
                    VRPlinkGetResponse(uniqueID = "openUrl", url = type.url, redirectURL = "type.url")
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
