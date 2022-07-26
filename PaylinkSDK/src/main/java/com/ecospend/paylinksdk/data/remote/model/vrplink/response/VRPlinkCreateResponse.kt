package com.ecospend.paylinksdk.data.remote.model.vrplink.response

import com.google.gson.annotations.SerializedName

data class VRPlinkCreateResponse(

    /**
     * A system assigned unique identification for the Paylink.
     * This value is also a part of the URL.
     */
    @SerializedName("unique_id")
    val uniqueID: String?,

    /**
     * Unique Paylink URL that you will need to redirect PSU in order the payment to proceed.
     */
    val url: String?,

    /**
     * Base64 encoded QRCode image data that represents Paylink URL.
     */
    @SerializedName("qr_code")
    val qrCode: String?,
)
