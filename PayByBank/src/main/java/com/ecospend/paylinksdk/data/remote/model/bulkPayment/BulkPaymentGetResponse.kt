package com.ecospend.paybybank.data.remote.model.bulkPayment

import com.ecospend.paybybank.data.remote.model.paylink.PayByBankAccountResponse
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankNotificationOptionsResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BulkPaymentGetResponse(

    /**
     * The URL to open bank selection screen
     */
    val url: String?,

    /**
     * Id of payment.
     */
    @SerializedName("payment_id")
    val paymentID: String?,

    /**
     * A system assigned unique identification for the Bulk Payment Paylink.
     */
    @SerializedName("unique_id")
    val uniqueID: String?,

    /**
     * Status of the Bulk Payment Paylink.
     * Note: Enum: "Initial" "AwaitingAuthorization" "Authorised" "Verified" "Completed" "Canceled" "Failed" "Rejected" "Abandoned"
     */
    val status: BulkPaymentStatus?,

    /**
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String?,

    /**
     * Bulk payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    @SerializedName("file_reference")
    val fileReference: String?,

    /**
     * Description for the payment. 255 character MAX.
     */
    val description: String?,

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of payment process.
     */
    @SerializedName("redirect_url")
    val redirectURL: String?,

    /**
     * Unique identification string assigned to the bank by our system.
     * If value is set, Paylink will not display any UI and execute an instant redirect to the debtor's banking system.
     * If value is not set, Paylink will display the PSU a bank selection screen.
     */
    @SerializedName("bank_id")
    val bankID: String?,

    /**
     * If you are providing our Payment service to your own business clients (merchants), then you should set the Id of your merchant.
     */
    @SerializedName("merchant_id")
    val merchantID: String?,

    /**
     * The Id of the end-user.
     * If you are providing this service directly to the end-users, then you can assign that Id to this parameter.
     * If you are providing this service to businesses, then you should assign the Id of that merchant’s user.
     */
    @SerializedName("merchant_user_id")
    val merchantUserID: String?,

    /**
     * It is the account from which the payment will be taken.
     */
    @SerializedName("debtor_account")
    val debtorAccount: PayByBankAccountResponse?,

    /**
     * The Paylink Options model
     */
    @SerializedName("paylink_options")
    val paylinkOptions: BulkPaymentPaylinkOptionsResponse?,

    /**
     * The Notification Options model
     */
    @SerializedName("notification_options")
    val notificationOptions: PayByBankNotificationOptionsResponse?,

    /**
     * The Payment Options model
     */
    @SerializedName("payment_options")
    val paymentOptions: BulkPaymentOptionsResponse?,

    /**
     * The Limit Options model
     */
    @SerializedName("limit_options")
    val limitOptions: BulkPaymentLimitOptionsResponse?,

    /**
     * Payments object for individual payments for the bulk payment.
     */
    val payments: List<BulkPaymentPaylinkEntryResponse>?
)

enum class BulkPaymentStatus : Serializable {
    @SerializedName("Initial")
    Initial,

    @SerializedName("AwaitingAuthorization")
    AwaitingAuthorization,

    @SerializedName("Authorised")
    Authorised,

    @SerializedName("Verified")
    Verified,

    @SerializedName("Completed")
    Completed,

    @SerializedName("Canceled")
    Canceled,

    @SerializedName("Failed")
    Failed,

    @SerializedName("Rejected")
    Rejected,

    @SerializedName("Abandoned")
    Abandoned
}

data class BulkPaymentPaylinkOptionsResponse(

    /**
     * Client id of the API consumer.
     */
    @SerializedName("client_id")
    val clientID: String?,

    /**
     * Tenant id of the API consumer.
     */
    @SerializedName("tenant_id")
    val tenantID: String?,

    /**
     * After the payment directly returns to the tenant's url if set to true.
     * Note: Defaults to false.
     */
    @SerializedName("auto_redirect")
    val autoRedirect: Boolean?,

    /**
     * If you are set true, no redirect after payment.
     */
    @SerializedName("dont_redirect")
    val dontRedirect: Boolean?,

    /**
     * If bank is pre-set on creation a temporary consent url for payment operation.
     */
    @SerializedName("temporary_consent_url")
    val temporaryConsentURL: String?,

    /**
     * Expire date of temporary consent url.
     */
    @SerializedName("temporary_consent_url_expire_date")
    val temporaryConsentURLExpireDate: String?,

    /**
     * Disables QR Code component on paylink.
     */
    @SerializedName("disable_qr_code")
    val disableQrCode: Boolean?,

    /**
     * Purpose of the bulk payment.
     */
    val purpose: String?
)

data class BulkPaymentOptionsResponse(

    /**
     * Defines the schedule date  for the payment in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("scheduled_for")
    val scheduledFor: String?,

    /**
     * Gets or sets the bulk payment rails.
     */
    @SerializedName("payment_rails")
    val paymentRails: String?,
)

data class BulkPaymentLimitOptionsResponse(

    /**
     * Expire date for the paylink in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    val date: String?,
)

data class BulkPaymentPaylinkEntryResponse(

    /**
     * It is the account that will receive the payment.
     */
    @SerializedName("creditor_account")
    val creditorAccount: PayByBankAccountResponse?,

    /**
     * Payment amount in decimal format.
     */
    val amount: Float?,

    /**
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String?,

    /**
     * Must be set to a date/time in GMT+0 in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("scheduled_for")
    val scheduledFor: String?,

    /**
     * Free text field for any client reference usage.
     */
    @SerializedName("client_reference_id")
    val clientReferenceID: String?
)
