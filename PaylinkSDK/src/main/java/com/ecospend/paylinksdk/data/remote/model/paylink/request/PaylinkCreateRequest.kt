package com.ecospend.paylinksdk.data.remote.model.paylink.request

import com.ecospend.paylinksdk.data.remote.model.PayByBankNotificationOptionsRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.PaylinkLimitOptions
import com.ecospend.paylinksdk.data.remote.model.paylink.PaylinkOptions
import com.ecospend.paylinksdk.data.remote.model.paylink.PaylinkPaymentOptions
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaylinkCreateRequest(

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of the paylink journey.
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
     * Unique identification string assigned to the bank by our system.
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

    /** The Id of the end-user.
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
     * The Paylink Options model
     */
    @SerializedName("paylink_options")
    val paylinkOptions: PaylinkOptions? = null,

    /**
     * The Notification Options model
     */
    @SerializedName("notification_options")
    val notificationOptions: PayByBankNotificationOptionsRequest? = null,

    /**
     * The PaymentOptions model
     */
    @SerializedName("payment_options")
    val paymentOptions: PaylinkPaymentOptions? = null,

    /**
     * The LimitOptions model
     */
    @SerializedName("limit_options")
    val limitOptions: PaylinkLimitOptions? = null
) : Serializable
