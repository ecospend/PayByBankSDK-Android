package com.ecospend.paylinksdk.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaylinkTipOption(

    // / Tip options type.
    // / Enum: "Amount" "Percentage" "Manual"
    val type: PaylinkTipOptionType? = null,

    // / Tip options value.
    // / - Can be left empy or set to zero(0) if type is manual.
    val value: Double? = null
) : Serializable

enum class PaylinkTipOptionType(val value: Int) : java.io.Serializable {
    @SerializedName("1")
    Amount(1),
    @SerializedName("2")
    Percentage(2),
    @SerializedName("3")
    Manual(3)
}
