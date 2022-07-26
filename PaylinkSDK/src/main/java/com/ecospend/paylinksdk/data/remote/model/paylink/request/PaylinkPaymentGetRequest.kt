package com.ecospend.paylinksdk.data.remote.model.paylink.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PaylinkPaymentGetRequest(

    /**
     * Paylink ID to query
     */
    @SerializedName("paylink_id")
    val paylinkID: String
) : Serializable
