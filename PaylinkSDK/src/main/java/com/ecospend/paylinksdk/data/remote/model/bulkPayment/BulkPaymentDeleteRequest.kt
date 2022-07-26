package com.ecospend.paylinksdk.data.remote.model.bulkPayment

import com.google.gson.annotations.SerializedName

data class BulkPaymentDeleteRequest(

    /**
     * Unique id value to delete Paylink
     */
    @SerializedName("unique_id")
    val uniqueID: String,
)
