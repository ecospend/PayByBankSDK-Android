package com.ecospend.paybybank.app.module.datalink

import android.app.Activity
import com.ecospend.paybybank.data.remote.model.datalink.response.DatalinkGetResponse
import com.ecospend.paybybank.data.remote.model.datalink.response.DatalinkModel
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
 * Datalink API
 * Note: Datalink is a whitelabel consent journey solution provided by Ecospend that downsizes the required implementation for the consent journey to a single endpoint integration. By making a single call to /datalink endpoint you will be able to initiate the consent journey.
 */
class Datalink() {

    /**
     *  Opens webview using with `url` of Datalink
     *
     *@property activity: Activty that provides to present bank selection
     *@property datalinkUrl:  Url of paylink.
     *@property redirectUrl:  Redirect url value of paylink.
     *@property completion: It provides to handle result or error
     */
    fun openWithUrl(
        activity: Activity,
        uniqueID: String,
        datalinkUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = DatalinkExecuteType.OpenWithUrl(uniqueID, datalinkUrl, redirectUrl),
            completion = completion
        )
    }

    //region Private Functions

    private fun execute(
        activity: Activity,
        type: DatalinkExecuteType,
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
        type: DatalinkExecuteType,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ): Triple<String, String, String>? {
        val response = when (type) {
            is DatalinkExecuteType.OpenWithUrl -> {
                withContext(Dispatchers.IO) {
                    DatalinkGetResponse(datalink = DatalinkModel(uniqueID = type.uniqueID, url = type.url), redirectURL = type.redirectUrl)
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
