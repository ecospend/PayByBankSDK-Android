package com.ecospend.paybybank.app.module.paylink

import android.app.Activity
import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.model.paylink.request.PaylinkCreateRequest
import com.ecospend.paybybank.data.remote.model.paylink.request.PaylinkDeleteRequest
import com.ecospend.paybybank.data.remote.model.paylink.request.PaylinkGetRequest
import com.ecospend.paybybank.data.remote.model.paylink.response.PaylinkCreateResponse
import com.ecospend.paybybank.data.remote.model.paylink.response.PaylinkGetResponse
import com.ecospend.paybybank.data.repository.IamRepository
import com.ecospend.paybybank.data.repository.PaylinkRepository
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
 * Paylink API
 * The Ecospend Gateway presents Paylink as an alternative and easier form of Open Banking Instant Payment solution.
 * Paylink provides you the option of downsizing the development effort for a PIS journey to a single endpoint integration.
 * Paylink undertakes all of interaction in the payment user journey with your branding on display.
 */
class Paylink(
    private val iamRepository: IamRepository,
    private val paylinkRepository: PaylinkRepository
) {

    /**
     *  Opens bank application or bank website using with request model of payment
     *
     *@property activity: Activty that provides to present bank selection
     *@property request: Request to create paylink
     *@property completion: It provides to handle result or error
     */
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

    /**
     *  Opens webview using with `uniqueID` of paylink
     *
     *@property activity: Activty that provides to present bank selection
     *@property uniqueID:  Unique id value of paylink.
     *@property completion: It provides to handle result or error
     */
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

    /**
     *  Opens webview using with `url` of paylink
     *
     *@property activity: Activty that provides to present bank selection
     *@property paylinkUrl:  Url of paylink.
     *@property redirectUrl:  Redirect url of paylink.
     *@property completion: It provides to handle result or error
     */
    fun openWithUrl(
        activity: Activity,
        uniqueID: String,
        paylinkUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = PaylinkExecuteType.OpenWithUrl(uniqueID, paylinkUrl, redirectUrl),
            completion = completion
        )
    }

    /**
     * Creates Paylink
     *
     *@property request: Request to create Paylink
     *@property completion: It provides to handle result or error
     */
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

    /**
     * Creates Paylink
     *
     *@property request: Request to create Paylink
     *@property completion: It provides to handle result or error
     */
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

    /**
     * Creates Paylink
     *
     *@property request: Request to create Paylink
     *@property completion: It provides to handle result or error
     */
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
            is PaylinkExecuteType.OpenWithUrl -> {
                withContext(Dispatchers.IO) {
                    PaylinkGetResponse(uniqueID = type.url, url = type.url, redirectURL = type.redirectUrl)
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
