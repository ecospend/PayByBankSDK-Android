package com.ecospend.paylinksdk.data.remote.model.frPayment

import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountResponse
import com.google.gson.annotations.SerializedName

data class FrPaymentGetResponse(

    /**
     * Unique id value of FrPayment.
     */
    @SerializedName("unique_id")
    val uniqueID: String?,

    /**
     * FrPayment amount in decimal format.
     */
    val amount: Float?,

    /**
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String?,

    /**
     * Description for the payment. 255 character MAX.
     */
    val description: String?,

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of the FrPayment journey.
     * This URL must be registered by your Admin on the Ecospend Management Console, prior to being used in the API calls.
     */
    @SerializedName("redirect_url")
    val redirectURL: String?,

    /**
     * The URL to open bank selection screen
     */
    val url: String?,

    /**
     * Unique identification string assigned to the bank by our system.
     * If value is set, FrPayment will not display any UI and execute an instant redirect to the debtor's banking system.
     * If value is not set, FrPayment will display the PSU a bank selection screen.
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
     * The Creditor Account model
     */
    @SerializedName("creditor_account")
    val creditorAccount: PayByBankAccountResponse?,

    /**
     * The Debtor Account model
     */
    @SerializedName("debtor_account")
    val debtorAccount: PayByBankAccountResponse?,

    /**
     * The FrPayment Options model
     */
    @SerializedName("fr_payment_options")
    val frPaymentOptions: FrPaymentOptionsResponse?,

    /**
     * Date and time of the first payment in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     * Warning: This date must be a work day.
     */
    @SerializedName("first_payment_date")
    val firstPaymentDate: String?,

    /**
     * Number of total payments being set with this standing order.
     */
    @SerializedName("number_of_payments")
    val numberOfPayments: Int?,

    /**
     * Period of FrPayment
     * Note: Enum: "Weekly" "Monthly" "Yearly"
     */
    val period: FrPaymentPeriod?,

    /**
     * The user has the right to change the FrPayment related additional parameters
     * Note: Defaults to false.
     */
    @SerializedName("allow_frp_customer_changes")
    val allowFrpCustomerChanges: Boolean?,
)

data class FrPaymentOptionsResponse(

    /**
     * Client id of the API consumer
     */
    @SerializedName("client_id")
    val clientID: String?,

    /**
     * Tenant id of the API consumer
     */
    @SerializedName("tenant_id")
    val tenantID: String?,

    /**
     * Set true, if you would like to get back the debtor's account information that the payment is made from.
     * Note: If not provided, defaults to 'true'.
     */
    @SerializedName("get_refund_info")
    val getRefundInfo: Boolean?,

    /**
     * Amount of first payment
     */
    @SerializedName("first_payment_amount")
    val firstPaymentAmount: Float?,

    /**
     * Amount of last payment
     */
    @SerializedName("last_payment_amount")
    val lastPaymentAmount: Float?,

    /**
     * Disables QR Code component on FrPayment
     */
    @SerializedName("disable_qr_code")
    val disableQrCode: Boolean?,

    /**
     * Customizes editable option of fields
     */
    @SerializedName("editable_fields")
    val editableFields: FrPaymentEditableField?,
)
