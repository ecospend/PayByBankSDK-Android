package com.ecospend.paylinksdk.ui.pay

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import androidx.core.view.contains
import com.ecospend.paylinksdk.shared.extensions.fromJson
import com.ecospend.paylinksdk.shared.extensions.queryParameters
import com.ecospend.paylinksdk.shared.extensions.trying
import com.ecospend.paylinksdk.shared.model.completion.PayByBankError
import com.ecospend.paylinksdk.shared.model.completion.PayByBankResult
import com.ecospend.paylinksdk.shared.model.completion.PayByBankStatus
import com.ecospend.paylinksdk.shared.model.redirect.RedirectEnumType
import com.ecospend.paylinksdk.shared.model.redirect.RedirectType
import com.ecospend.paylinksdk.ui.pay.model.AppWebViewUIModel
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

class AppWebView(private val uiModel: AppWebViewUIModel) {

    private var activityView: ViewGroup? = null
    private var mainView: RelativeLayout? = null

    fun open(activity: Activity?) {

        //region View

        activity ?: run {
            uiModel.completion(null, PayByBankError.ActivityError("Activity is null."))
            return
        }

        activityView = activity.findViewById(android.R.id.content)
        activityView ?: run {
            uiModel.completion(null, PayByBankError.ActivityError("Activity ViewGroup not found."))
            return
        }

        mainView = relativeLayout(activity)

        //endregion

        //region ViewModel

        activityView?.addView(mainView)
        val webView = webView(activity, uiModel.webViewURL, uiModel.redirectURL) { mainView }
        webView.loadUrl(uiModel.webViewURL)

        //endregion
    }

    fun removeViews() {
        mainView?.visibility = View.GONE
        activityView?.removeView(mainView)
    }

    //region View

    private fun relativeLayout(activity: Activity) = RelativeLayout(activity)
        .apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
        }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webView(activity: Activity, url: String, redirectUrl: String?, mainViewInstance: () -> RelativeLayout?) =
        WebView(activity)
            .apply {
                val paylinkHttpUrl = url.toHttpUrlOrNull()
                val paylinkRedirectHttpUrl = redirectUrl?.toHttpUrlOrNull()

                layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        return handleWebViewUrl(
                            activity = activity,
                            redirectUrl = url,
                            httlUrl = paylinkHttpUrl,
                            redirectHttpUrl = paylinkRedirectHttpUrl
                        )
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        mainViewInstance()?.let {
                            if (!it.contains(view as View)) {
                                it.addView(view)
                                uiModel.completion(
                                    PayByBankResult(
                                        uniqueID = uiModel.uniqueID,
                                        status = PayByBankStatus.Initiated
                                    ),
                                    null
                                )
                            }
                        }
                    }
                }
            }

    private fun handleWebViewUrl(
        activity: Activity,
        redirectUrl: String,
        httlUrl: HttpUrl?,
        redirectHttpUrl: HttpUrl?,
    ): Boolean {
        val uri = trying { Uri.parse(redirectUrl) } ?: return false
        val httpUrl = redirectUrl.toHttpUrlOrNull()
        return when {
            redirectHttpUrl?.host == httpUrl?.host -> {
                when {
                    redirectUrl.contains(RedirectEnumType.UserCanceled.value) -> {
                        removeViews()
                        val model = uri.queryParameters.fromJson<RedirectType.UserCancelled>()
                        uiModel.completion(
                            PayByBankResult(
                                uniqueID = uiModel.uniqueID,
                                status = PayByBankStatus.Cancelled
                            ),
                            null
                        )
                    }
                }
                return true
            }
            httlUrl?.host == httpUrl?.host &&
                httlUrl != null &&
                httpUrl != null -> {
                false
            }
            else -> {
                removeViews()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl))
                activity.startActivity(intent)
                uiModel.completion(
                    PayByBankResult(
                        uniqueID = uiModel.uniqueID,
                        status = PayByBankStatus.Initiated
                    ),
                    null
                )
                true
            }
        }
    }

    //endregion
}
