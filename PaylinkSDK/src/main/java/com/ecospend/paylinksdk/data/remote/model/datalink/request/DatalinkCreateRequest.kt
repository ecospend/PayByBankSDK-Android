package com.ecospend.paylinksdk.data.remote.model.datalink.request

import com.ecospend.paylinksdk.data.remote.model.PayByBankNotificationOptionsRequest
import com.ecospend.paylinksdk.data.remote.model.datalink.DatalinkOptions
import com.ecospend.paylinksdk.data.remote.model.datalink.FinancialReport
import com.ecospend.paylinksdk.data.remote.model.datalink.response.ConsentPermission
import com.google.gson.annotations.SerializedName

data class DatalinkCreateRequest(

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of account access process.
     */
    @SerializedName("redirect_url")
    val redirectURL: String? = null,

    /**
     * Unique identification string assigned to the bank by our system.
     * If value is set, Datalink will not display any UI and execute an instant redirect to the debtor's banking system.
     * If value is not set, Datalink will display the PSU a bank selection screen.
     */
    @SerializedName("bank_id")
    val bankId: String? = null,

    /**
     * If you are providing our Data service to your own business clients (merchants), then you should set the Id of your merchant.
     */
    @SerializedName("merchant_id")
    val merchantId: String? = null,

    /**
     * The Id of the end-user.
     * If you are providing this service directly to the end-users, then you can assign that Id to this parameter.
     * If you are providing this service to businesses, then you should assign the Id of that merchant’s user.
     */
    @SerializedName("merchant_user_id")
    val merchantUserId: String? = null,

    /**
     * The date indicating when consent will end.
     */
    @SerializedName("consent_end_date")
    val consentEndDate: String? = null,

    /**
     * The date indicating when consent will expire.
     */
    @SerializedName("expiry_date")
    val expiryDate: String? = null,

    /**
     * The permissions which will be asked to the users while connecting their account.
     * Determines which data will be fetched
     * If it is not set, system will automatically set all the permissions
     */
    val permissions: List<ConsentPermission>? = null,

    @SerializedName("datalink_options")
    val datalinkOptions: DatalinkOptions? = null,

    @SerializedName("notification_options")
    val notificationOptions: PayByBankNotificationOptionsRequest? = null,

    @SerializedName("financial_report")
    val financialReport: FinancialReport? = null
)
