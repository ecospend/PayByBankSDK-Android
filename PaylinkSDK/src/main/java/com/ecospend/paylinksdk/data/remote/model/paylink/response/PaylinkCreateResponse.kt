package com.ecospend.paylinksdk.data.remote.model.paylink.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaylinkCreateResponse(

    /**
     * A system assigned unique identification for the Paylink.
     * This value is also a part of the URL.
     */
    @SerializedName("unique_id")
    val uniqueID: String? = null,

    /**
     * Unique Paylink URL that you will need to redirect PSU in order the payment to proceed.
     */
    @SerializedName("url")
    val paylinkURL: String? = null,

    /**
     * Base64 encoded QRCode image data that represents Paylink URL.
     */
    @SerializedName("qr_code")
    val qrCode: String? = null
) : Serializable
