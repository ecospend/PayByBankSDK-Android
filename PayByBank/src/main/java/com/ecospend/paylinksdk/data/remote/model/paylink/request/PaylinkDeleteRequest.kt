package com.ecospend.paybybank.data.remote.model.paylink.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PaylinkDeleteRequest(

    /**
     * Paylink ID to delete
     */
    @SerializedName("paylink_id")
    val paylinkID: String
) : Serializable
