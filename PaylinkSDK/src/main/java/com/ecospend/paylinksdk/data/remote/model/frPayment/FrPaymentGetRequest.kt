package com.ecospend.paylinksdk.data.remote.model.frPayment

import com.google.gson.annotations.SerializedName

data class FrPaymentGetRequest(

    /**
     * Unique id value to query FrPayment
     */
    @SerializedName("unique_id")
    val uniqueID: String
)
