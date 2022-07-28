package com.ecospend.paybybank.data.remote.model.bulkPayment

import com.google.gson.annotations.SerializedName

class BulkPaymentGetRequest(
    /**
     * Unique id value to query Bulk Payment Paylink
     */
    @SerializedName("unique_id")
    val uniqueID: String,
)
