package com.ecospend.paybybank.shared.network.mock

import android.content.Context
import com.ecospend.paybybank.shared.helper.ResourceHelper.loadJsonFromAssets
import com.ecospend.paybybank.shared.network.type.Header
import com.ecospend.paybybank.shared.network.type.Mime
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

enum class Method(val value: String) {
    Get("GET"),
    Post("POST"),
    Put("PUT"),
    Delete("DELETE"),
    Patch("PATCH")
}

class MockInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val method = chain.request().method
        val responseString: String = when {
            uri.endsWith(MockAPIType.ExampleService.api) -> loadJsonFromAssets(
                context,
                MockAPIType.ExampleService.fileName
            )
            else -> ""
        }
        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(responseString.toResponseBody(Mime.Application.JSON.toMediaTypeOrNull()))
            .addHeader(Header.ContentType.key, Header.ContentType.value)
            .build()
    }
}
