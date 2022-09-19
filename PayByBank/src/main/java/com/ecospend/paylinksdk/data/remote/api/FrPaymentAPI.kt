package com.ecospend.paybybank.data.remote.api

import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentGetResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FrPaymentAPI {

    suspend fun createFrPayment(
        @Body model: FrPaymentCreateRequest?
    ): FrPaymentCreateResponse?

    suspend fun getFrPayment(
        @Path("uniqueID") uniqueID: String
    ): FrPaymentGetResponse?

    suspend fun deleteFrPayment(
        @Path("uniqueID") uniqueID: String
    ): Boolean?
}
