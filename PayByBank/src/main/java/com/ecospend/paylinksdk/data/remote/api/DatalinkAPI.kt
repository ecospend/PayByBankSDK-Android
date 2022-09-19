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

    suspend fun createDatalink(
        @Body model: DatalinkCreateRequest
    ): DatalinkCreateResponse?

    suspend fun getDatalink(
        @Path("uniqueID") uniqueID: String
    ): DatalinkGetResponse?

    suspend fun deleteDatalink(
        @Path("uniqueID") uniqueID: String
    ): Boolean?

    suspend fun getDatalinkOfConsent(
        @Path("consentID") consentID: String
    ): DatalinkGetResponse?
}
