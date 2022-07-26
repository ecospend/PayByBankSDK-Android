package com.ecospend.paylinksdk.data.remote.model.vrplink.response

import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankNotificationOptionsResponse
import com.ecospend.paylinksdk.data.remote.model.vrplink.VRPType
import com.ecospend.paylinksdk.data.remote.model.vrplink.request.VRPAlignment
import com.ecospend.paylinksdk.data.remote.model.vrplink.request.VRPReason
import com.google.gson.annotations.SerializedName

data class VRPlinkGetResponse(

    /**
     * Unique id value of paylink.
     */
    @SerializedName("unique_id")
    val uniqueID: String?,

    /**
     * Payment reference that will be displayed on the bank statement. 18 characters MAX.
     */
    val reference: String?,

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
     * The URL to open bank selection screen
     */
    val url: String?,

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
     * It determines which type of payment operation will be executed by the Gateway.
     * Enum: "Sweeping" "Vrp"
     */
    val type: VRPType?,

    /**
     * It determines which reason of payment operation will be executed by the Gateway.
     * Enum: "None" "PartyToParty" "BillPayment" "EcommerceGoods" "EcommerceServices" "Other"
     */
    val reason: VRPReason?,

    /**
     * It provides to verify the account that will receive the payment
     */
    @SerializedName("verify_creditor_account")
    val verifyCreditorAccount: Boolean?,

    /**
     * It provides to verify the account from which the payment will be taken.
     */
    @SerializedName("verify_debtor_account")
    val verifyDebtorAccount: Boolean?,

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
     * The VRP Options model
     */
    @SerializedName("vrp_options")
    val vrpOptions: VRPOptionsResponse?,

    /**
     * The VRP Limit Options Options model
     */
    @SerializedName("limit_options")
    val limitOptions: VRPLimitOptionsResponse?,

    /**
     * The Notification Options model
     */
    @SerializedName("notification_options")
    val notificationOptions: PayByBankNotificationOptionsResponse?,

    /**
     * The VRPlink Options model
     */
    @SerializedName("vrplink_options")
    val vrplinkOptions: VRPlinkOptionsResponse?,

    /**
     * The VRPlink Limit Options model
     */
    @SerializedName("vrplink_limit_options")
    val vrplinkLimitOptions: VRPlinkLimitOptionsResponse?,
)

data class VRPLimitOptionsResponse(

    /**
     * Currency code in ISO 4217 format.
     * Enum: "GBP" "USD" "EUR"
     */
    val currency: PayByBankCurrency?,

    /**
     * Maximum single payment amount in decimal format.
     */
    @SerializedName("single_payment_amount")
    val singlePaymentAmount: Float?,

    /**
     * Daily payment amount limit in decimal format.
     */
    @SerializedName("daily_amount")
    val dailyAmount: Float?,

    /**
     * Weekly payment amount limit in decimal format.
     */
    @SerializedName("weekly_amount")
    val weeklyAmount: Float?,

    /**
     * Fortnightly payment amount limit in decimal format.
     */
    @SerializedName("fortnightly_amount")
    val fortnightlyAmount: Float?,

    /**
     * Monthly payment amount limit in decimal format.
     */
    @SerializedName("monthly_amount")
    val monthlyAmount: Float?,

    /**
     * HalfYearly payment amount limit in decimal format.
     */
    @SerializedName("half_yearly_amoung")
    val halfYearlyAmount: Float?,

    /**
     * Yearly payment amount limit in decimal format.
     */
    @SerializedName("yearly_amount")
    val yearlyAmount: Float?,

    /**
     * Daily payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("daily_alignment")
    val dailyAlignment: VRPAlignment?,

    /**
     * Weekly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("weekly_alignment")
    val weeklyAlignment: VRPAlignment?,

    /**
     * Fortnightly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("fortnightly_alignment")
    val fortnightlyAlignment: VRPAlignment?,

    /**
     * Monthly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("monthly_alignment")
    val monthlyAlignment: VRPAlignment?,

    /**
     * HalfYearly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("half_yearly_alignment")
    val halfYearlyAlignment: VRPAlignment?,

    /**
     * Yearly payment alignment
     * Enum: "Consent" "Calendar"
     */
    @SerializedName("yearly_alignment")
    val yearlyAlignment: VRPAlignment?,
)

data class VRPOptionsResponse(

    /**
     * Indicates Validity Start Date in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("valid_from")
    val validFrom: String?,

    /**
     * Indicates Validity End Date in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("valid_to")
    val validTo: String?,

    /**
     * Set true, if you would like to get back the debtor's account information that the payment is made from.
     * Note: If not provided, defaults to 'true'.
     */
    @SerializedName("get_refund_info")
    val getRefundInfo: Boolean?
)

data class VRPlinkLimitOptionsResponse(

    /**
     * Expire date for the paylink in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    val date: String?
)

data class VRPlinkOptionsResponse(

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
     * After the payment directly returns to the tenant's url if set to true.
     */
    @SerializedName("auto_redirect")
    val autoRedirect: Boolean?,

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
     * If you are set true, no redirect after vrp.
     */
    @SerializedName("dont_redirect")
    val dontRedirect: Boolean?
)
