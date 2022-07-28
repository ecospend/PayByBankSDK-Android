package com.ecospend.paybybank.data.remote.api

import com.ecospend.paybybank.data.remote.model.datalink.request.DatalinkCreateRequest
import com.ecospend.paybybank.data.remote.model.datalink.response.DatalinkCreateResponse
import com.ecospend.paybybank.data.remote.model.datalink.response.DatalinkGetResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DatalinkAPI {

    @POST("datalink")
    suspend fun createDatalink(
        @Body model: DatalinkCreateRequest
    ): DatalinkCreateResponse?

    @GET("datalink/{uniqueID}")
    suspend fun getDatalink(
        @Path("uniqueID") uniqueID: String
    ): DatalinkGetResponse?

    @DELETE("datalink/{uniqueID}")
    suspend fun deleteDatalink(
        @Path("uniqueID") uniqueID: String
    ): Boolean?

    @GET("datalink/consent/{consentID}")
    suspend fun getDatalinkOfConsent(
        @Path("consentID") consentID: String
    ): DatalinkGetResponse?
}
