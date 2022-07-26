package com.ecospend.paylinksdk.shared.network.interceptor

import com.ecospend.paylinksdk.app.PayByBankState
import com.ecospend.paylinksdk.app.urls
import com.ecospend.paylinksdk.shared.Config
import com.ecospend.paylinksdk.shared.logger.AppLog
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URISyntaxException

class HostInterceptor : Interceptor {

    var host = Config.Network.apiBaseUrl

    override fun intercept(chain: Interceptor.Chain): Response {
        host = Config.Network.apiBaseUrl
        var request = chain.request()
        var newUrl: HttpUrl? = null
        try {
            val requestUrl = "${request.url.scheme}://${request.url.host}/"
            val url = PayByBankState.Config.environment
                .urls()
                .find { it.contains(requestUrl) } ?: requestUrl

            newUrl = if (host != url) {
                request.url.toString().replace(url, host).toHttpUrlOrNull()
            } else {
                request.url.toString().toHttpUrlOrNull()
            }
        } catch (e: URISyntaxException) {
            AppLog.i(e.localizedMessage ?: e.message ?: "")
        }
        newUrl?.let {
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }
        val response = chain.proceed(request)
        return response
    }
}
