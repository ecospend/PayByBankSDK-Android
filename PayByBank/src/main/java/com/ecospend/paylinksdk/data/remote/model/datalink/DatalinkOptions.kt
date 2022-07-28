package com.ecospend.paybybank.data.remote.model.datalink

import com.google.gson.annotations.SerializedName

data class DatalinkOptions(

    /**
     * Determines if the PSU will see a completed page by Ecospend.
     * If it is set true, then PSU will be redirected directly to Tenant's redirect page If it is set false, then PSU will see the consent completed page wihch provided by the Ecospend
     */

    @SerializedName("auto_redirect")
    val autoRedirect: Boolean?,

    /**
     * Optional parameter for getting a QRCode image in Base64 format with the response.
     * Defaults to false.
     */
    // /
    @SerializedName("generate_qr_code")
    val generateQrCode: Boolean?,

    /**
     * Optional parameter for allowing user to create consent multiply
     * When this value is set, datalink will be ask for connect an another account at the end of journey.
     * Defaults to false.
     */
    @SerializedName("allow_multiple_consent")
    val allowMultipleConsent: Boolean?,

    /**
     * Optional parameter to enable generating financial report
     * When this value is set, datalink will be give an option to redirect financial report at the end of journey.
     * Defaults to false.
     */
    @SerializedName("generate_financial_report")
    val generateFinancialReport: Boolean?

)
