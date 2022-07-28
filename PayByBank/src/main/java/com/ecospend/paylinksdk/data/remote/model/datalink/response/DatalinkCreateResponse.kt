package com.ecospend.paybybank.data.remote.model.datalink.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DatalinkCreateResponse(

    /**
     * A system assigned unique identification for the Datalink.
     * This value is also a part of the URL.
     */
    @SerializedName("unique_id")
    val uniqueID: String?,

    /**
     * Unique Datalink URL that you will need to redirect PSU in order the account access consent to proceed.
     */
    val url: String?,

    /**
     * Base64 encoded QRCode image data that represents Datalink URL.
     */
    @SerializedName("qr_code")
    val qrCode: String?

) : Serializable
