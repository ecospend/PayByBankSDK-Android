package com.ecospend.paybybank.data.remote.model.datalink.request

import com.google.gson.annotations.SerializedName

data class DatalinkGetRequest(

    @SerializedName("unique_id")
    val uniqueID: String
)
