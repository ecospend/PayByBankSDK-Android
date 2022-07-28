package com.ecospend.paybybank.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PayByBankAccountRequest(

    /**
     * Enum: "SortCode" "Iban" "Bban"
     */
    val type: PayByBankAccountType? = null,

    /**
     * Account identification. The value of this parameter depends on the value of AccountType.
     * If type = “SortCode” then a 6-digit SortCode appended with a 8-digit Account Number merged into a 14-digit value, with no dashes in between.
     * For type = “IBAN” the IBAN of the account (compliant with [ISO 13616-1](https://en.wikipedia.org/wiki/International_Bank_Account_Number#Structure)) and for type = “BNAN” the BBAN of the account must be set.
     */
    val identification: String? = null,

    /**
     * Full legal name of the account owner.
     */
    val name: String? = null,

    @SerializedName("owner_name")
    val ownerName: String? = null,

    /**
     * Currency code of the account in [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) format.
     * Enum: "GBP" "USD" "EUR"
     */
    val currency: PayByBankCurrency? = null
) : Serializable
