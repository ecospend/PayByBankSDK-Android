package com.ecospend.paybybank.data.remote.model.payment.request

import com.ecospend.paybybank.data.remote.model.paylink.PayByBankAccountRequest
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankCurrency
import com.google.gson.annotations.SerializedName

data class PaymentCreateRefundRequest(

    /**
     * Unique id value to query Payment
     */
    val id: String,

    /**
     * Unique identification string assigned to the bank by our system.
     */
    @SerializedName("bank_id")
    val bankID: String,

    /**
     * Payment amount in decimal format.
     * Warning: This amount can not exceed original payment amount.
     */
    val amount: Float,

    /**
     * Currency code  in [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) format.
     * Note: Enum: "GBP" "USD" "EUR"
     */
    val currency: PayByBankCurrency,

    /**
     * Description for the payment. 255 character MAX.
     */
    val description: String?,

    /**
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String,

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of payment process.
     */
    @SerializedName("redirect_url")
    val redirectURL: String,

    /**
     * Represents the refund account information structure of that is returned by the bank.
     */
    @SerializedName("refund_account")
    val refundAccount: PayByBankAccountRequest?
)
