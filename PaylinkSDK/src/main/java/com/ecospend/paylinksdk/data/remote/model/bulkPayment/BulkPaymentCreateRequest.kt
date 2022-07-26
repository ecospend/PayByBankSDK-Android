package com.ecospend.paylinksdk.data.remote.model.bulkPayment

import com.ecospend.paylinksdk.data.remote.model.PayByBankNotificationOptionsRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountRequest
import com.google.gson.annotations.SerializedName

data class BulkPaymentCreateRequest(

    /**
     * Unique identification string assigned to the bank by our system.)
     */
    val bankID: String? = null,

    /**
     * It is the account from which the payment will be taken.
     */
    @SerializedName("debtor_account")
    val debtorAccount: PayByBankAccountRequest? = null,

    /**
     * Description for the payment. 255 character MAX.
     */
    val description: String? = null,

    /**
     * Bulk payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    @SerializedName("file_reference")
    val fileReference: String? = null,

    /**
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String? = null,

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of payment process.
     */
    @SerializedName("redirect_url")
    val redirectURL: String? = null,

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
    @SerializedName("GBP")
    val merchantUserID: String? = null,

    /**
     * The Payment Options model
     */
    @SerializedName("payment_options")
    val paymentOptions: BulkPaymentOptions? = null,

    /**
     * The Paylink Options model
     */
    val options: BulkPaymentPaylinkOptions? = null,

    /**
     * The Notification Options model
     */
    @SerializedName("notification_options")
    val notificationOptions: PayByBankNotificationOptionsRequest? = null,

    /**
     * The Limit Options model.
     */
    @SerializedName("limit_options")
    val limitOptions: BulkPaymentLimitOptions? = null,

    /**
     * Payments object for individual payments for the bulk payment.
     */
    val payments: List<BulkPaymentPaylinkEntry>? = null
)

data class BulkPaymentLimitOptions(

    /**
     * Expire date for the paylink in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    val date: String?
)

data class BulkPaymentPaylinkOptions(

    /**
     * After the payment directly returns to the tenant's url if set to true.
     * Note: Defaults to false.
     */
    @SerializedName("auto_redirect")
    val autoRedirect: Boolean?,

    /**
     * Optional parameter for getting a QRCode image in Base64 format with the response.
     * Note: Defaults to false.
     */
    @SerializedName("generate_qr_code")
    val generateQrCode: Boolean?,

    /**
     * Disables QR Code component on Paylinks
     */
    @SerializedName("disable_qr_code")
    val disableQrCode: Boolean?,

    /**
     * Purpose of the bulk payment.
     */
    val purpose: String?,
)

data class BulkPaymentOptions(

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

data class BulkPaymentPaylinkEntry(

    /**
     * It is the account that will receive the payment.
     */
    @SerializedName("creditor_account")
    val creditorAccount: PayByBankAccountRequest? = null,

    /**
     * Payment amount in decimal format.
     */
    val amount: Float? = null,

    /**
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String? = null,

    /**
     * Must be set to a date/time in GMT+0 in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("scheduled_for")
    val scheduledFor: String? = null,

    /**
     * Free text field for any client reference usage.
     */
    @SerializedName("client_reference_id")
    val clientReferenceID: String? = null,
)
