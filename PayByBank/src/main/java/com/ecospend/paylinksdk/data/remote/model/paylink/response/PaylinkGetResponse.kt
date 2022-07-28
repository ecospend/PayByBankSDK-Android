package com.ecospend.paybybank.data.remote.model.paylink.response

import com.ecospend.paybybank.data.remote.model.paylink.PayByBankAccountResponse
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankNotificationOptionsResponse
import com.ecospend.paybybank.data.remote.model.paylink.PaylinkLimitOptionsResponse
import com.ecospend.paybybank.data.remote.model.paylink.PaylinkOptionsResponse
import com.ecospend.paybybank.data.remote.model.paylink.PaylinkPaymentOptionsResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PaylinkGetResponse(

    /**
     * Unique id value of paylink.
     */
    @SerializedName("unique_id")
    val uniqueID: String? = null,

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
     * The URL of the Tenant that the PSU will be redirected at the end of the paylink journey.
     * This URL must be registered by your Admin on the Ecospend Management Console, prior to being used in the API calls.
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
     * The Paylink Options model
     */
    @SerializedName("paylink_options")
    val paylinkOptions: PaylinkOptionsResponse? = null,

    /**
     * The Notification Options model
     */
    @SerializedName("notification_options")
    val notificationOptions: PayByBankNotificationOptionsResponse? = null,

    /**
     * The PaymentOptions model
     */
    @SerializedName("payment_options")
    val paymentOptions: PaylinkPaymentOptionsResponse? = null,

    /**
     * The LimitOptions model
     */
    @SerializedName("limit_options")
    val limitOptions: PaylinkLimitOptionsResponse? = null,

    val url: String?
) : Serializable
