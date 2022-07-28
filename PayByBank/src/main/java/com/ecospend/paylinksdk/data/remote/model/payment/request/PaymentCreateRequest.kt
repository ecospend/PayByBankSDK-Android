package com.ecospend.paybybank.data.remote.model.payment.request

import com.ecospend.paybybank.data.remote.model.paylink.PayByBankAccountRequest
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paybybank.data.remote.model.payment.PaymentType
import com.google.gson.annotations.SerializedName

data class PaymentCreateRequest(

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of the payment journey.
     * Warning: This URL must be registered by your Admin on the Ecospend Management Console, prior to being used in the API calls.
     */
    @SerializedName("redirect_url")
    val redirectURL: String? = null,

    /**
     * Unique identification string assigned to the bank by our system.
     */
    @SerializedName("bank_id")
    val bankID: String? = null,

    /**
     * Payment amount in decimal format.
     */
    val amount: Float? = null,

    /**
     * Currency code  in [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) format.
     * Note: Enum: "GBP" "USD" "EUR"
     */
    val currency: PayByBankCurrency? = null,

    /**
     * Description for the payment. 255 character MAX.
     */
    val description: String? = null,

    /**
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String? = null,

    /**
     * If you are providing our Payment service to your own business clients (merchants), then you should set the Id of your merchant.
     */
    @SerializedName("merchant_id")
    val merchantID: String? = null,

    /**
     * The Id of the end-user.
     * If you are providing this service directly to the end-users, then you can assign that Id to this parameter.
     * If you are providing this service to businesses, then you should assign the Id of that merchant’s user.
     */
    @SerializedName("merchant_user_id")
    val merchantUserID: String? = null,

    /**
     * It is the account that will receive the payment.
     */
    @SerializedName("creditor_account")
    val creditorAccount: PayByBankAccountRequest? = null,

    /**
     * It is the account from which the payment will be taken.
     */
    @SerializedName("debtor_account")
    val debtorAccount: PayByBankAccountRequest? = null,

    /**
     * Additional fields for a payment that can be mandotary for specific cases.
     */
    @SerializedName("payment_option")
    val paymentOption: PaymentOption? = null,

    /**
     * It determines which type of payment operation will be executed by the Gateway.
     * Note: Enum: "Auto" "Domestic" "DomesticScheduled" "International" "InternationalScheduled"
     */
    @SerializedName("payment_type")
    val paymentType: PaymentType? = null

)

data class PaymentOption(

    /**
     * Set true, if you would like to get back the debtor's account information that the payment is made from.
     * Note: If not provided, defaults to 'false'.
     */
    @SerializedName("get_refund_info")
    val getRefundInfo: Boolean?,

    /**
     * Set true, if the payment is being created with a possiblity of future payout operation.
     * Note: If not provided, defaults to 'false'. If provided as 'true', overwrides `getRefundInfo` to 'true'.
     * Warning: Will respond with an error if set 'true' and selected bank does not support refund and is not a direct Faster Payment participant.
     * (see: [/banks response model](https://docs.ecospend.com/api/pis/V2/#tag/Banks/paths/~1api~1v2.0~1banks~1{id}/get))
     */
    @SerializedName("for_payout")
    val forPayout: Boolean?,

    /**
     * If provided, our system automatically converts the payment into a Scheduled Payment in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     * Warning: Must be set to a future date/time (it must be the next day or later) in GMT+0.
     */
    @SerializedName("scheduled_for")
    val scheduledFor: String?,

    /**
     * Mandatory information for Berlin Group and STET specifications.
     */
    @SerializedName("psu_id")
    val psuID: String?,

    /**
     * The underlying payment rails that the bank transfers the money.
     * Note: If not provided, “FasterPayments” is used as the default rails for the UK. Alternatives being “BACS” and “CHAPS”.
     */
    @SerializedName("payment_rails")
    val paymentRails: String?
)
