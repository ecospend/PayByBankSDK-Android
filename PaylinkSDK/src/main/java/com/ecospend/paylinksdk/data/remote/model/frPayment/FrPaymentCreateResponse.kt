package com.ecospend.paylinksdk.data.remote.model.frPayment

import com.google.gson.annotations.SerializedName

data class FrPaymentCreateResponse(

    /**
     * A system assigned unique identification for the FrPayment.
     * This value is also a part of the URL.
     */
    @SerializedName("unique_id")
    val uniqueID: String?,

    /**
     * Unique FrPayment URL that you will need to redirect PSU in order the payment to proceed.
     */
    val url: String?,

    /**
     * Base64 encoded QRCode image data that represents FrPayment URL.
     */
    @SerializedName("qr_code")
    val qrCode: String?,
)
