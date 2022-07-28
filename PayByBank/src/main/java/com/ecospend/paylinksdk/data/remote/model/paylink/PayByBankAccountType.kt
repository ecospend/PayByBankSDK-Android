package com.ecospend.paybybank.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class PayByBankAccountType : Serializable {
    @SerializedName("SortCode")
    SortCode,
    @SerializedName("Iban")
    Iban,
    @SerializedName("Bban")
    Bban
}
