package com.ecospend.paybybank.data.remote.model.vrplink.request

import com.google.gson.annotations.SerializedName

data class VRPlinkGetRequest(

    /**
     * Unique id value to query VRPlink
     */
    @SerializedName("unique_id")
    val uniqueID: String
)
