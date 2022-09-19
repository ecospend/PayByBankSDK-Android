package com.ecospend.paybybank.app.module.paylink

import android.app.Activity
import com.ecospend.paybybank.data.remote.model.paylink.response.PaylinkGetResponse
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
 * Paylink API
 * The Ecospend Gateway presents Paylink as an alternative and easier form of Open Banking Instant Payment solution.
 * Paylink provides you the option of downsizing the development effort for a PIS journey to a single endpoint integration.
 * Paylink undertakes all of interaction in the payment user journey with your branding on display.
 */
class Paylink() {

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

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: PaylinkExecuteType,
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
        type: PaylinkExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {
            is PaylinkExecuteType.OpenWithUrl -> {
                withContext(Dispatchers.IO) {
                    PaylinkGetResponse(uniqueID = type.url, url = type.url, redirectURL = type.redirectUrl)
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

    //endregion
}
