package com.ecospend.paylinksdk.data.remote.model.paylink.response

import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paylinksdk.data.remote.model.paylink.PaylinkPaymentOptionsResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.PaylinkPaymentStatus
import com.ecospend.paylinksdk.data.remote.model.paylink.PaylinkPaymentType
import com.ecospend.paylinksdk.data.remote.model.paylink.PaylinkRefundAccountResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PaylinkPaymentGetResponse(

    /**
     * Paylink Id of the payment
     */
    @SerializedName("unique_id")
    val uniqueID: String? = null,

    /**
     * Client Id of the payment
     */
    @SerializedName("client_id")
    val clientID: String? = null,

    /**
     * Client Id of the payment
     */
    val id: String? = null,

    /**
     * An identification number for the payment that is assigned by the bank. Can have different formats for each bank.
     */
    @SerializedName("bank_reference_id")
    val bankReferenceID: String? = null,

    /**
     * A unique and one time use only URL from the PSU’s bank. You will need to redirect the PSU to this link for them to authorise a payment.
     */
    @SerializedName("date_created")
    val dateCreated: String? = null,

    /**
     * Unique identification string assigned to the bank by our system.
     */
    @SerializedName("bank_id")
    val bankID: String? = null,

    /**
     * Initial: PaymentRequest is made but a PaymentResponse is not provided yet.
     * AwaitingAuthorization: A PaymentResponse, including bank's payment URL is returned and the PSU is expected to authorise the payment.
     * Authorised: The PSU has authorized the payment from their banking system.
     * Verified: Ecospend and the PSU’s Bank verified the payment authorization. This does not necessarily mean that the money has been received by the creditor account.
     * Completed: Payment is completed, and transfer is made.
     * Canceled: The PSU has cancelled the payment flow.
     * Failed: Payment flow has failed due to an error.
     * Rejected: Bank has rejected the payment.
     * Abandoned: The PSU has deserted the payment journey prior to being redirected back from the bank
     */
    val status: PaylinkPaymentStatus? = null,

    /**
     * Payment amount in decimal format.
     */
    val amount: Float? = null,

    /**
     * Currency code of the account in [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217#Active_codes) format.
     * Enum: "GBP" "USD" "EUR"
     */
    val currency: PayByBankCurrency? = null,

    /**
     * The description that you provided with the PaymentRequest (if any).
     */
    val description: String? = null,

    /**
     * The reference that you provided with the PaymentRequest.
     */
    val reference: String? = null,

    /**
     * The merchant_id that you provided with the PaymentRequest (if any).
     */
    @SerializedName("merchant_id")
    val merchantID: String? = null,

    /**
     * The merchant_user_id that you provided with the PaymentRequest (if any).
     */
    @SerializedName("merchant_user_id")
    val merchantUserID: String? = null,

    @SerializedName("original_payment_id")
    val originalPaymentID: String? = null,

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of payment process.
     */
    @SerializedName("redirect_url")
    val redirectURL: String? = null,

    /**
     * Indicates the paylink is auto-redirect
     */
    @SerializedName("auto_redirect")
    val autoRedirect: Boolean? = null,

    /**
     * payment_type determines which type of payment operation will be executed by the Gateway.
     * Enum: "Auto" "Domestic" "DomesticScheduled" "International" "InternationalScheduled"
     */
    @SerializedName("payment_type")
    val paymentType: PaylinkPaymentType? = null,

    /**
     * The Creditor Account model
     */
    @SerializedName("creditor_account")
    val creditorAccount: PayByBankAccountResponse? = null,

    /**
     * The Debtor Account model
     */
    @SerializedName("debtor_account")
    val debtorAccount: PayByBankAccountResponse? = null,

    /**
     * The PaymentOptions model
     */
    @SerializedName("payment_options")
    val paymentOptions: PaylinkPaymentOptionsResponse? = null,

    /**
     * The Refund Account model
     */
    @SerializedName("refund_account")
    val refundAccount: PaylinkRefundAccountResponse? = null,

    /**
     * Detailed message about the failure reason of the payment (if any).
     */
    @SerializedName("failure_message")
    val failureMessage: String? = null
) : Serializable
