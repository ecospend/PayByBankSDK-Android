package com.ecospend.paybybank.data.remote.model.bulkPayment

import com.google.gson.annotations.SerializedName

data class BulkPaymentCreateResponse(

    /**
     * A system assigned unique identification for the Bulk Payment Paylink.
     * This value is also a part of the URL.
     */
    @SerializedName("unique_id")
    val uniqueID: String?,

    /**
     * Unique Bulk Payment Paylink URL that you will need to redirect PSU in order the payment to proceed.
     */
    val url: String?,

    /**
     * Base64 encoded QRCode image data that represents Bulk Payment Paylink URL.
     */
    @SerializedName("qr_code")
    val qrCode: String?,
)
