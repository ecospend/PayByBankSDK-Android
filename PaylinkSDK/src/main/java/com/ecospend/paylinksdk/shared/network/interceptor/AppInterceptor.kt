package com.ecospend.paylinksdk.shared.network.interceptor

import com.ecospend.paylinksdk.app.PayByBankState
import com.ecospend.paylinksdk.shared.extensions.trying
import com.ecospend.paylinksdk.shared.logger.AppLog
import com.ecospend.paylinksdk.shared.network.type.Header
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer

class AppInterceptor : Interceptor {
    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.apply {
            request().newBuilder().apply {
                addHeader(
                    Header.Authorization.key,
                    Header.Authorization.value.format(PayByBankState.Network.tokenResponse?.accessToken)
                )
            }.run {
                val response = proceed(build())
                val rawJson = response.body?.string()
                runBlocking {
                    launch {
                        logServiceCall(
                            response = response,
                            rawJson = rawJson
                        )
                    }
                }
                return response
                    .newBuilder()
                    .body((rawJson ?: "").toResponseBody(response.body?.contentType()))
                    .build()
            }
        }
    }

    private fun logServiceCall(response: Response, rawJson: String?) {
        val authorization = response.request.header(Header.Authorization.key)
        val method = response.request.method

        val path = response.request.url.encodedPath
        val query = if (!response.request.url.query.isNullOrBlank()) "?${response.request.url.query}" else ""
        val requestBody = trying {
            if (response.request.body?.contentType()?.subtype != "json") return@trying ""
            val buffer = Buffer()
            response.request.body?.writeTo(buffer) ?: return@trying ""
            return@trying buffer.readUtf8()
        }
        val status = if (response.isSuccessful) "success" else "error"
        var responseBody = rawJson ?: ""
        if (response.request.url.encodedPath.contains("assets/localization")) {
            responseBody = responseBody.replace("%s", "[SPD]")
        }
        AppLog.i("AUTHORIZATION: $authorization")
        AppLog.i("REQUEST: $method $path$query $requestBody")
        AppLog.i("RESPONSE ($status): $responseBody")
    }
}
