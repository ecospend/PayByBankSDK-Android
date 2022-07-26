package com.ecospend.paylinksdk.data.remote.api

import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkCreateRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.response.PaylinkCreateResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.response.PaylinkGetResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.response.PaylinkPaymentGetResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaylinkAPI {

    @POST("paylinks")
    suspend fun createPaylink(
        @Body model: PaylinkCreateRequest
    ): PaylinkCreateResponse?

    @GET("paylinks/{paylinkID}")
    suspend fun getPaylink(
        @Path("paylinkID") paylinkID: String
    ): PaylinkGetResponse?

    @DELETE("paylinks/{paylinkID}")
    suspend fun deletePaylink(
        @Path("paylinkID") paylinkID: String
    ): Boolean?

    @GET("paylinks/{paylinkID}/payments")
    suspend fun getPayments(
        @Path("paylinkID") paylinkID: String
    ): List<PaylinkPaymentGetResponse>?
}
