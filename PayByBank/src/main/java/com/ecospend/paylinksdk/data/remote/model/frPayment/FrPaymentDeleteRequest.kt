package com.ecospend.paybybank.data.remote.model.frPayment

import com.google.gson.annotations.SerializedName

class FrPaymentDeleteRequest(
    /**
     * Unique id value to query FrPayment
     */
    @SerializedName("unique_id")
    val uniqueID: String
)
