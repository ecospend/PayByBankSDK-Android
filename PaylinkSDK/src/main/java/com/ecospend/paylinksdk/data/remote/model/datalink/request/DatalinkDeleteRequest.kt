package com.ecospend.paylinksdk.data.remote.model.datalink.request

import com.google.gson.annotations.SerializedName

data class DatalinkDeleteRequest(

    @SerializedName("unique_id")
    val uniqueID: String
)
