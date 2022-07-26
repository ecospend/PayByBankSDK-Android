package com.ecospend.paylinksdk.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class PayByBankCurrency : Serializable {
    @SerializedName("GBP")
    GBP,
    @SerializedName("USD")
    USD,
    @SerializedName("EUR")
    EUR
}
