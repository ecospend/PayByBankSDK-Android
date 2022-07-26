package com.ecospend.paylinksdk.data.remote.model.paylink

import java.io.Serializable

class PayByBankAccountResponse(

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
    val name: String? = null,

    /**
     * Currency code of the account in [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) format.
     * Enum: "GBP" "USD" "EUR"
     */
    val currency: PayByBankCurrency? = null,

    /**
     * The bic that you provided with the PaymentRequest (if any).
     */
    val bic: String? = null
) : Serializable
