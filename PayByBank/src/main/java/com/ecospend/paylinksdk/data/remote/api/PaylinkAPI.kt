package com.ecospend.paybybank.data.remote.api

import com.ecospend.paybybank.data.remote.model.paylink.request.PaylinkCreateRequest
import com.ecospend.paybybank.data.remote.model.paylink.response.PaylinkCreateResponse
import com.ecospend.paybybank.data.remote.model.paylink.response.PaylinkGetResponse
import com.ecospend.paybybank.data.remote.model.paylink.response.PaylinkPaymentGetResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaylinkAPI {

    suspend fun createPaylink(
        @Body model: PaylinkCreateRequest
    ): PaylinkCreateResponse?

    suspend fun getPaylink(
        @Path("paylinkID") paylinkID: String
    ): PaylinkGetResponse?

    suspend fun deletePaylink(
        @Path("paylinkID") paylinkID: String
    ): Boolean?

    suspend fun getPayments(
        @Path("paylinkID") paylinkID: String
    ): List<PaylinkPaymentGetResponse>?
}
