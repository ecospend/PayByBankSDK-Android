package com.ecospend.paylinksdk.data.remote.model.payment

import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class PaymentType : Serializable {
    @SerializedName("Auto")
    Auto,

    @SerializedName("Domestic")
    Domestic,

    @SerializedName("DomesticScheduled")
    DomesticScheduled,

    @SerializedName("International")
    International,

    @SerializedName("InternationalScheduled")
    InternationalScheduled,
}
