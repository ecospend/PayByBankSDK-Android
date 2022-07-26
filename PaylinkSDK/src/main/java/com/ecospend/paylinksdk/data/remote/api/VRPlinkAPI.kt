package com.ecospend.paylinksdk.data.remote.api

import com.ecospend.paylinksdk.data.remote.model.vrplink.request.VRPlinkCreateRequest
import com.ecospend.paylinksdk.data.remote.model.vrplink.response.VRPlinkCreateResponse
import com.ecospend.paylinksdk.data.remote.model.vrplink.response.VRPlinkGetRecordsResponse
import com.ecospend.paylinksdk.data.remote.model.vrplink.response.VRPlinkGetResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VRPlinkAPI {

    @POST("vrplinks")
    suspend fun createVRPlink(
        @Body model: VRPlinkCreateRequest
    ): VRPlinkCreateResponse?

    @GET("vrplinks/{uniqueID}")
    suspend fun getVRPlink(
        @Path("uniqueID") uniqueID: String
    ): VRPlinkGetResponse?

    @DELETE("vrplinks/{uniqueID}")
    suspend fun deleteVRPlink(
        @Path("uniqueID") uniqueID: String
    ): Boolean?

    @GET("vrplinks/{uniqueID}/vrps")
    suspend fun getVRPlinkRecords(
        @Path("uniqueID") uniqueID: String
    ): List<VRPlinkGetRecordsResponse>?
}
