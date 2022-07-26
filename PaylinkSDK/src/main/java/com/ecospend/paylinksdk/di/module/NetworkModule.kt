package com.ecospend.paylinksdk.di.module

import com.ecospend.paylinksdk.di.core.EcoDi
import com.ecospend.paylinksdk.shared.Config
import com.ecospend.paylinksdk.shared.network.interceptor.AppInterceptor
import com.ecospend.paylinksdk.shared.network.interceptor.ErrorInterceptor
import com.ecospend.paylinksdk.shared.network.interceptor.HostInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule

fun NetworkModule.inject() {

    /** Provides: HostInterceptor */
    EcoDi.provide { HostInterceptor() }

    /** Provides: AppInterceptor */
    EcoDi.provide { AppInterceptor() }

    /** Provides: ErrorInterceptor */
    EcoDi.provide { ErrorInterceptor() }

    /** Provides: OkHttpClient */
    EcoDi.provide {
        val hostInterceptor = EcoDi.inject<HostInterceptor>() as Interceptor
        val appInterceptor = EcoDi.inject<AppInterceptor>() as Interceptor
        val errorInterceptor = EcoDi.inject<ErrorInterceptor>() as Interceptor

        OkHttpClient.Builder()
            .connectTimeout(Config.Network.API_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Config.Network.API_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(appInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(hostInterceptor)
            .build()
    }

    /** Provides: Retrofit */
    EcoDi.provide {
        val okHttpClient = EcoDi.inject<OkHttpClient>()
        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(Config.Network.apiBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
