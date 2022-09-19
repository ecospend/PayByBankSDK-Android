package com.ecospend.paybybank.app.module.vrplink

import android.app.Activity
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkGetResponse
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
 * VRPlink (Variable Recurring Payments) API
 * Note: Variable Recurring Payments (VRPs) let customers safely connect authorised payments providers to their bank account so that they can make payments on the customer’s behalf,
 * in line with agreed limits. VRPs offer more control and transparency than existing alternatives, such as Direct Debit payments.
 */
class VRPlink() {

    /**
     *  Opens webview using with `url` of vrpLink
     *
     *@property activity: Activty that provides to present bank selection
     *@property vrpUrl:  Vrp of vrpLink.
     *@property redirectUrl:  Redirect url of vrpLink.
     *@property completion: It provides to handle result or error
     */
    fun openWithUrl(
        activity: Activity,
        uniqueID: String,
        vrpUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = VRPlinkExecuteType.OpenWithUrl(uniqueID, vrpUrl, redirectUrl),
            completion = completion
        )
    }

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: VRPlinkExecuteType,
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
        type: VRPlinkExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {
            is VRPlinkExecuteType.OpenWithUrl -> {
                withContext(Dispatchers.IO) {
                    VRPlinkGetResponse(uniqueID = type.uniqueID, url = type.url, redirectURL = type.redirectUrl)
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
        if (!url.contains("ecospend.com")) {
            completion(null, PayByBankError.WrongPaylink("Url must contain Ecospend services"))
        }
        val redirectURL = response.redirectURL
            ?: run {
                completion(null, PayByBankError.WrongPaylink("redirectUrl Error."))
                return null
            }

        return Triple(id, url, redirectURL)
    }

//endregion
}
