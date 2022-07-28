package com.ecospend.paybybank.data.remote.model.vrplink.request

import com.google.gson.annotations.SerializedName

data class VRPlinkDeleteRequest(

    /**
     * Unique id value to delete VRPlink
     */
    @SerializedName("unique_id")
    val uniqueID: String
)
