package com.ecospend.paylinksdk.data.remote.api

import com.ecospend.paylinksdk.data.remote.model.frPayment.FrPaymentCreateRequest
import com.ecospend.paylinksdk.data.remote.model.frPayment.FrPaymentCreateResponse
import com.ecospend.paylinksdk.data.remote.model.frPayment.FrPaymentGetResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FrPaymentAPI {

    @POST("fr-payments")
    suspend fun createFrPayment(
        @Body model: FrPaymentCreateRequest?
    ): FrPaymentCreateResponse?

    @GET("fr-payments/{uniqueID}")
    suspend fun getFrPayment(
        @Path("uniqueID") uniqueID: String
    ): FrPaymentGetResponse?

    @DELETE("fr-payments/{uniqueID}")
    suspend fun deleteFrPayment(
        @Path("uniqueID") uniqueID: String
    ): Boolean?
}
