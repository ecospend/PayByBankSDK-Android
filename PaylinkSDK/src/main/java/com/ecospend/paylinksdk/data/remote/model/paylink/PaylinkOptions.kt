package com.ecospend.paylinksdk.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaylinkOptions(

    /**
     * After the payment directly returns to the tenant's url if set to true.
     * Defaults to false.
     */
    @SerializedName("auto_redirect")
    val autoRedirect: Boolean? = null,

    /**
     * Optional parameter for getting a QRCode image in Base64 format with the response.
     * Defaults to false.
     */
    @SerializedName("generate_qr_code")
    val generateQrCode: Boolean? = null,

    /**
     * Optional parameter for allowing user to pay total amount partially.
     * Warning: When this value is set, paylink will be expired total amount is comppublic leted.
     * Defaults to false.
     */
    @SerializedName("allow_partial_payments")
    val allowPartialPayments: Boolean? = null,

    /**
     * Optional parameter for displaying a QR Code on the paylink screens, that enables users to transfer their journey from desktop to mobile easily. This feature is only visible on desktop view.
     */
    @SerializedName("disable_qr_code")
    val disableQrCode: Boolean? = null,

    /**
     * The Tip object model
     */
    val tip: PaylinkTip? = null
) : Serializable
