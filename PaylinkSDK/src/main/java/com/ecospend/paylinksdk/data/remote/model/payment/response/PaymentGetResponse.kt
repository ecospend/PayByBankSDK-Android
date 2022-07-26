package com.ecospend.paylinksdk.data.remote.model.payment.response

import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paylinksdk.data.remote.model.payment.PaymentType
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaymentGetResponse(

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
     * Status of the payment
     * Note: Enum: "Initial" "AwaitingAuthorization" "Authorised" "Verified" "Completed" "Canceled" "Failed" "Rejected" "Abandoned"
     */
    val status: PaymentStatus?,

    /**
     * Indicates if the payment is a refund.
     */
    @SerializedName("is_refund")
    val isRefund: Boolean?,

    /**
     * If `isRefund`='true', provides the payment_id of the original payment that this refund is created for.
     */
    @SerializedName("original_payment_id")
    val originalPaymentID: String?,

    /**
     * The URL submitted with the Request.
     */
    @SerializedName("redirect_url")
    val redirectURL: String?,

    /**
     * The URL to open bank application or website
     */
    val url: String?,

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
     * It is the account that will receive the payment.
     */
    @SerializedName("creditor_account")
    val creditorAccount: PayByBankAccountResponse?,

    /**
     * It is the account from which the payment will be taken.
     */

    @SerializedName("debtor_account")
    val debtorAccount: PayByBankAccountResponse?,

    /**
     * Additional fields for the payment request.
     */

    @SerializedName("payment_option")
    val paymentOption: PaymentOptionResponse?,

    /**
     * Represents the refund account information structure of that is returned by the bank.
     */
    @SerializedName("refund_account")
    val refundAccount: PayByBankAccountResponse?,

    /**
     * Indicates if the payment transaction is settled on the creditor account. Available with the [optional]
     * Reconciliation Feature.
     */
    @SerializedName("is_reconciled")
    val isReconciled: Boolean?,

    /**
     * Date and time information that is gathered from the creditor account statement by the [optional]
     * Reconciliation Feature in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("reconciliation_date")
    val reconciliationDate: String?,
)

enum class PaymentStatus : Serializable {

    /**
     * Your Request POST call to the /payments endpoint is received and registered and the Ecospend Gateway
     * is currently interacting with the bank's system to respond you with a Response.
     * This is a very brief interim state that will NOT trigger webhooks.
     */
    @SerializedName("Initial")
    Initial,

    /**
     * A Response, including bank's payment URL is successfully responded to your call and the PSU
     * is expected to authorise the payment with your redirection.
     */
    @SerializedName("AwaitingAuthorization")
    AwaitingAuthorization,

    /**
     * The PSU has authorized the payment from their banking system.
     * This is a very brief interim state that will NOT trigger webhooks.
     */
    @SerializedName("Authorised")
    Authorised,

    /**
     * The ASPSP has authorized and accepted the payment for processing.
     * The payment is not completed yet, it has not been sent through the corresponding payment rails. The payment can still be rejected by the ASPSP.
     */
    @SerializedName("Verified")
    Verified,

    /**
     * The Payment is considered as completed when the payer's ASPSP has sent the payment
     * through the corresponding payment rails to be credited to the payee account.
     */
    @SerializedName("Completed")
    Completed,

    /**
     * The PSU has cancelled the payment flow.
     */
    @SerializedName("Canceled")
    Canceled,

    /**
     * Payment flow has failed due to an error.
     */
    @SerializedName("Failed")
    Failed,

    /**
     * Bank has rejected the payment.
     */
    @SerializedName("Rejected")
    Rejected,

    /**
     * The PSU has never initiated the payment journey or deserted the payment
     * journey prior to authorizing the payment on their banking system.
     */
    @SerializedName("Abandoned")
    Abandoned,
}
