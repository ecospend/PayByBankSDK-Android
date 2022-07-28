package com.ecospend.paybybank.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaylinkRefundAccountResponse(

    /**
     * Enum: "SortCode" "Iban" "Bban"
     */
    val type: PayByBankAccountType? = null,

    /**
     * The identification that you provided with the request.
     */
    val identification: String? = null,

    /**
     * The owner_name that you provided with the PaymentRequest.
     */
    @SerializedName("owner_name")
    val ownerName: String? = null
) : Serializable
