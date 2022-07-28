package com.ecospend.paybybank.data.remote.model.vrplink.request

import com.ecospend.paybybank.data.remote.model.PayByBankNotificationOptionsRequest
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankAccountRequest
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paybybank.data.remote.model.vrplink.VRPType
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VRPlinkCreateRequest(

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of payment process.
     */
    @SerializedName("redirect_url")
    val redirectURL: String? = null,

    /**
     * Unique identification string assigned to the bank by our system.
     * If value is set, Paylink will not display any UI and execute an instant redirect to the debtor's banking system.
     * If value is not set, Paylink will display the PSU a bank selection screen.
     */
    @SerializedName("bank_id")
    val bankID: String? = null,

    /**
     * It determines which reason of payment operation will be executed by the Gateway.
     * Enum: "None" "PartyToParty" "BillPayment" "EcommerceGoods" "EcommerceServices" "Other"
     */
    val reason: VRPReason? = null,

    /**
     * It determines which type of payment operation will be executed by the Gateway.
     * Enum: "Sweeping" "Vrp"
     */
    val type: VRPType? = null,

    /**
     * It provides to verify the account that will receive the payment.
     */
    @SerializedName("verify_creditor_account")
    val verifyCreditorAccount: Boolean? = null,

    /**
     * It provides to verify the account from which the payment will be taken.
     */
    @SerializedName("verify_debtor_account")
    val verifyDebtorAccount: Boolean? = null,

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
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String? = null,

    /**
     * Description for the payment. 255 character MAX.
     */
    val description: String? = null,

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
     * The VRP Options model
     */
    @SerializedName("vrp_options")
    val vrpOptions: VRPOptions? = null,

    /**
     * The VRP Limit Options Options model
     */
    @SerializedName("limit_options")
    val limitOptions: VRPLimitOptions? = null,

    /**
     * The Notification Options model
     */
    @SerializedName("notification_options")
    val notificationOptions: PayByBankNotificationOptionsRequest? = null,

    /**
     * The VRPlink Options model
     */
    @SerializedName("vrplink_options")
    val vrplinkOptions: VRPlinkOptions? = null,

    /**
     * The VRPlink Limit Options model
     */
    @SerializedName("vrplink_limit_options")
    val vrplinkLimitOptions: VRPlinkLimitOptions? = null,
)

enum class VRPReason : Serializable {
    @SerializedName("None")
    None,

    @SerializedName("PartyToParty")
    PartyToParty,

    @SerializedName("BillPayment")
    BillPayment,

    @SerializedName("EcommerceGoods")
    EcommerceGoods,

    @SerializedName("EcommerceServices")
    EcommerceServices,

    @SerializedName("Other")
    Other
}

data class VRPLimitOptions(

    /**
     * Currency code in ISO 4217 format.
     * Enum: "GBP" "USD" "EUR"
     */
    val currency: PayByBankCurrency? = null,

    /**
     * Maximum single payment amount in decimal format.
     */
    @SerializedName("single_payment_amount")
    val singlePaymentAmount: Float? = null,

    /**
     * Daily payment amount limit in decimal format.
     */
    @SerializedName("daily_amount")
    val dailyAmount: Float? = null,

    /**
     * Weekly payment amount limit in decimal format.
     */
    @SerializedName("weekly_amount")
    val weeklyAmount: Float? = null,

    /**
     * Fortnightly payment amount limit in decimal format.
     */
    @SerializedName("fortnightly_amount")
    val fortnightlyAmount: Float? = null,

    /**
     * Monthly payment amount limit in decimal format.
     */
    @SerializedName("monthly_amount")
    val monthlyAmount: Float? = null,

    /**
     * HalfYearly payment amount limit in decimal format.
     */
    @SerializedName("half_yearly_amount")
    val halfYearlyAmount: Float? = null,

    /**
     * Yearly payment amount limit in decimal format.
     */
    @SerializedName("yearly_amount")
    val yearlyAmount: Float? = null,

    /**
     * Daily payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("daily_alignment")
    val dailyAlignment: VRPAlignment? = null,

    /**
     * Weekly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("weekly_alignment")
    val weeklyAlignment: VRPAlignment? = null,

    /**
     * Fortnightly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("fortnightly_alignment")
    val fortnightlyAlignment: VRPAlignment? = null,

    /**
     * Monthly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("monthly_alignment")
    val monthlyAlignment: VRPAlignment? = null,

    /**
     * HalfYearly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("half_yearly_alignment")
    val halfYearlyAlignment: VRPAlignment? = null,

    /**
     * Yearly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("yearly_alignment")
    val yearlyAlignment: VRPAlignment? = null,
)

enum class VRPAlignment : Serializable {
    @SerializedName("Consent")
    Consent,

    @SerializedName("Calendar")
    Calendar
}

data class VRPOptions(

    /**
     * Indicates Validity Start Date in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("valid_from")

    val validFrom: String? = null,

    /**
     * Indicates Validity End Date in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("valid_to")

    val validTo: String? = null,

    /**
     * Set true, if you would like to get back the debtor's account information that the payment is made from.
     * Note: If not provided, defaults to 'true'.
     */
    @SerializedName("get_refund_info")
    val getRefundInfo: Boolean? = null,
)

data class VRPlinkLimitOptions(

    /**
     * Expire date for the paylink in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    val date: String? = null,
)

data class VRPlinkOptions(

    /**
     * Optional parameter for getting a QRCode image in Base64 format with the response.
     * Note: Defaults to false.
     */
    @SerializedName("generate_qr_code")
    val generateQrCode: Boolean? = null,

    /**
     * Disables QR Code component on Paylinks
     */
    @SerializedName("disable_qr_code")
    val disableQrCode: Boolean? = null,

    /**
     * After the payment directly returns to the tenant's url if set to true.
     */
    @SerializedName("auto_redirect")
    val autoRedirect: Boolean? = null,

    /**
     * If you are set true, no redirect after vrp.
     */
    @SerializedName("dont_redirect")
    val dontRedirect: Boolean? = null,
)
