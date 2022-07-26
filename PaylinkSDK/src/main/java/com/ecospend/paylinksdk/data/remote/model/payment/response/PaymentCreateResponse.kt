package com.ecospend.paylinksdk.data.remote.model.payment.response

import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paylinksdk.data.remote.model.payment.PaymentType
import com.google.gson.annotations.SerializedName

data class PaymentCreateResponse(

    /**
     * A system assigned unique identification for the payment.
     * You may need to use this id to query payments or initiate a refund.
     */
    val id: String?,

    /**
     * An identification number for the payment that is assigned by the bank.
     * Can have different formats for each bank.
     */
    @SerializedName("bank_reference_id")
    val bankReferenceID: String?,

    /**
     * Initiation date and time of the payment request in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("date_created")
    val dateCreated: String?,

    /**
     * A unique and one time use only URL of the debtor's banking system.
     * You will need to redirect PSU to this link in order the payment to proceed.
     */
    @SerializedName("payment_url")
    val paymentURL: String?,

    /**
     * Status of the Payment Initiation
     * Note: Enum: "AwaitingAuthorization"
     */
    val status: PaymentInitiationStatus?,

    /**
     * Indicates if the payment is a refund.
     */
    @SerializedName("is_refund")

    val isRefund: Boolean?,

    /**
     * The URL submitted with the Request.
     */
    @SerializedName("redirect_url")
    val redirectURL: String?,

    /**
     * The `bankID` value submitted with the Request.
     */
    @SerializedName("bank_id")
    val bankID: String?,

    /**
     * The `amount` value submitted with the Request.
     */
    val amount: Float?,

    /**
     * Currency code  in [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) format.
     * Note: Enum: "GBP" "USD" "EUR"
     */
    val currency: PayByBankCurrency?,

    /**
     * The `description` value submitted with the Request.
     */
    val description: String?,

    /**
     * The `reference` value submitted with the Request.
     */
    val reference: String?,

    /**
     * The `merchantID` value submitted with the Request.
     */
    @SerializedName("merchant_id")
    val merchantID: String?,

    /**
     * The `merchantUserID` value submitted with the Request.
     */
    @SerializedName("merchant_user_id")
    val merchantUserID: String?,

    /**
     * It determines which type of payment operation will be executed by the Gateway.
     * Note: Enum: "Auto" "Domestic" "DomesticScheduled" "International" "InternationalScheduled"
     */
    @SerializedName("payment_type")
    val paymentType: PaymentType?,

    /**
     * It is the account that will receive the payment./
     */
    @SerializedName("creditor_account")
    val creditorAccount: PayByBankAccountResponse?,

    /**
     * It is the account from which the payment will be taken.
     */
    @SerializedName("debtor_account")
    val debtorAccount: PayByBankAccountResponse?,

    /**
     * Additional fields for a payment that can be mandotary for specific cases.
     */
    @SerializedName("payment_option")
    val paymentOption: PaymentOptionResponse?
)

enum class PaymentInitiationStatus {

    /**
     * A PaymentResponse, including bank's payment URL is successfully responded to your call and
     * the PSU is expected to authorise the payment with your redirection.
     */
    @SerializedName("AwaitingAuthorization")
    AwaitingAuthorization
}

data class PaymentOptionResponse(

    /**
     * The `getRefundInfo` value that you provided with the Request (if any).
     */
    @SerializedName("get_refund_info")
    val getRefundInfo: Boolean?,

    /**
     * The `forPayout` value that you provided with the Request (if any).
     */
    @SerializedName("for_payout")
    val forPayout: Boolean?,

    /**
     * The `scheduledFor` value that you provided with the Request (if any).
     */
    @SerializedName("scheduled_for")
    val scheduledFor: String?,

    /**
     * The `psuID` value that you provided with the Request (if any).
     */
    @SerializedName("psu_id")
    val psuID: String?,

    /**
     * The `paymentRails` value that you provided with the Request (if any).
     */
    @SerializedName("payment_rails")
    val paymentRails: String?
)
