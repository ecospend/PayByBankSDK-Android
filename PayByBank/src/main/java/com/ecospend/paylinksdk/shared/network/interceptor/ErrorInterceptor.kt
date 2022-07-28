package com.ecospend.paybybank.shared.network.interceptor

import com.ecospend.paybybank.shared.extensions.fromJson
import com.ecospend.paybybank.shared.model.RepositoryErrorResponse
import com.ecospend.paybybank.shared.model.completion.NetworkError
import com.ecospend.paybybank.shared.model.completion.PayByBankError
import com.ecospend.paybybank.shared.model.completion.PayByBankResult
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ErrorInterceptor : Interceptor {

    companion object {
        var errorHandler: ((PayByBankResult?, PayByBankError?) -> Unit)? = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val response = chain.proceed(request)
        val responseBody = response.peekBody(Long.MAX_VALUE)
        val rawJson = responseBody.string()

//        Handler(Looper.getMainLooper()).post {
//            Toast.makeText(Paylink.activity, "${response.code} \n ${response.body} \n $rawJson", Toast.LENGTH_LONG).show()
//        }

        when (response.code) {
            in 200..299 -> {
                if (rawJson.isBlank()) {
                    errorHandler?.invoke(
                        null,
                        PayByBankError.Network(NetworkError.NoData)
                    )
                }
            }
            in 400..499 -> {
                val data = rawJson.fromJson<RepositoryErrorResponse?>()
                errorHandler?.invoke(
                    null,
                    PayByBankError.Network(
                        NetworkError.BadRequest(
                            code = response.code,
                            error = data?.localizedDescription,
                        )
                    )
                )
            }
            in 500..599 -> {
                val data = rawJson.fromJson<RepositoryErrorResponse?>()
                errorHandler?.invoke(
                    null,
                    PayByBankError.Network(
                        NetworkError.ServerError(
                            code = response.code,
                            error = data?.localizedDescription,
                        )
                    )
                )
            }
        }

        return response
    }
}
