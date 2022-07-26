package com.ecospend.paylinksdk.data.remote.model.vrplink.request

import com.google.gson.annotations.SerializedName

data class VRPlinkGetRecordsRequest(

    /**
     * Unique id value to delete VRPlink
     */
    @SerializedName("unique_id")
    val uniqueID: String,
)
