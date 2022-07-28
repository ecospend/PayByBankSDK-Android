package com.ecospend.paybybank.data.remote.model.frPayment

import com.ecospend.paybybank.data.remote.model.PayByBankNotificationOptionsRequest
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankAccountRequest
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FrPaymentCreateRequest(

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of the FrPayment journey.
     * This URL must be registered by your Admin on the Ecospend Management Console, prior to being used in the API calls.
     */
    @SerializedName("redirect_url")
    val redirectURL: String? = null,

    /**
     * Payment amount in decimal format.
     */
    val amount: Float? = null,

    /**
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String? = null,

    /**
     * Description for the payment. 255 character MAX.
     */
    val description: String? = null,

    /**
     * Description for the payment. 255 character MAX.
     * If value is set, Paylink will not display any UI and execute an instant redirect to the debtor's banking system.
     * If value is not set, Paylink will display the PSU a bank selection screen.
     */
    @SerializedName("bank_id")
    val bankID: String? = null,

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
     * Date and time of the first payment in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     * Warning: This date must be a work day.
     */
    @SerializedName("first_payment_date")
    val firstPaymentDate: String? = null,

    /**
     * Number of total payments being set with this standing order.
     */
    @SerializedName("number_of_payments")
    val numberOfPayments: Int? = null,

    /**
     * Period of FrPayment
     * Note: Enum: "Weekly" "Monthly" "Yearly"
     */
    val period: FrPaymentPeriod? = null,

    /**
     * Standing order type can be Domestic, International or Auto.
     * Warning: When 'Auto' is set, Gateway will determine the exact type according to the provided `bankID` and `creditorAccount` details.
     * Note: Enum: "Auto" "Domestic" "International"
     */
    @SerializedName("standing_order_type")
    val standingOrderType: FrPaymentStandingOrderType? = null,

    /**
     * The FrPayment Options model
     */
    @SerializedName("fr_payment_options")
    val frPaymentOptions: FrPaymentOptions? = null,

    /**
     * The user has the right to change the FrPayment related additional parameters
     * Note: Defaults to false.
     */
    @SerializedName("allow_frp_customer_changes")
    val allowFrpCustomerChanges: Boolean? = null,

    /**
     * The Notification Options model
     */
    @SerializedName("notification_options")
    val notificationOptions: PayByBankNotificationOptionsRequest? = null,
)

enum class FrPaymentPeriod : Serializable {
    @SerializedName("0")
    Weekly,
    @SerializedName("1")
    Monthly,
    @SerializedName("2")
    Yearly
}

enum class FrPaymentStandingOrderType : Serializable {
    @SerializedName("Auto")
    Auto,
    @SerializedName("Domestic")
    Domestic,
    @SerializedName("International")
    International
}

data class FrPaymentOptions(

    /**
     * Set true, if you would like to get back the debtor's account information that the payment is made from.
     * Note: Defaults to true.
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
     * After the payment directly returns to the tenant's url if set to true.
     * Note: Default is false.
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
     * Disables QR Code component on FrPayment
     */
    @SerializedName("disable_qr_code")
    val disableQrCode: Boolean?,

    /**
     * Customizes editable options of fields
     */
    @SerializedName("editable_fields")
    val editableFields: FrPaymentEditableField?,
)

data class FrPaymentEditableField(

    /**
     * Editable status of first payment date field
     */
    @SerializedName("first_payment_date")
    val firstPaymentDate: Boolean?,

    /**
     * Editable status of first payment amount field
     */
    @SerializedName("first_payment_amount")
    val firstPaymentAmount: Boolean?,

    /**
     * Editable status of last payment date field
     */
    @SerializedName("last_payment_amount")
    val lastPaymentAmount: Boolean?,

    /**
     * Editable status of  amount field
     */
    val amount: Boolean?,

    /**
     * Editable status of  period field
     */
    val period: Boolean?,

    /**
     * Editable status of  number of payments field
     */
    @SerializedName("number_of_payments")
    val numberOfPayments: Boolean?,
)
