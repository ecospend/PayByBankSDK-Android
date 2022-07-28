package com.ecospend.paybybank.data.remote.model.datalink.request

import com.google.gson.annotations.SerializedName

data class DatalinkGetConsentDatalinkRequest(

    @SerializedName("consent_id")
    val consentId: String
)
