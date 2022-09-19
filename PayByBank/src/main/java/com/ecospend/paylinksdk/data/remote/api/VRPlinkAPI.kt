package com.ecospend.paybybank.data.remote.api

import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkCreateRequest
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkCreateResponse
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkGetRecordsResponse
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkGetResponse
import retrofit2.http.Body
import retrofit2.http.Path

interface VRPlinkAPI {

    suspend fun createVRPlink(
        @Body model: VRPlinkCreateRequest
    ): VRPlinkCreateResponse?

    suspend fun getVRPlink(
        @Path("uniqueID") uniqueID: String
    ): VRPlinkGetResponse?

    suspend fun deleteVRPlink(
        @Path("uniqueID") uniqueID: String
    ): Boolean?

    suspend fun getVRPlinkRecords(
        @Path("uniqueID") uniqueID: String
    ): List<VRPlinkGetRecordsResponse>?
}
