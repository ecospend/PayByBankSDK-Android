package com.ecospend.paybybank.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PaylinkOptionsResponse(

    /**
     * After the payment directly returns to the tenant's url if set to true.
     * Defaults to false.
     */
    @SerializedName("auto_redirect")
    val autoRedirect: Boolean? = null,

    /**
     * True if the paylink allows partial payments, false otherwise.
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
    val tip: PaylinkTipResponse? = null
) : Serializable
