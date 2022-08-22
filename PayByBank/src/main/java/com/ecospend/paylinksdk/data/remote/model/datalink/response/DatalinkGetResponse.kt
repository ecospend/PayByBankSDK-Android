package com.ecospend.paybybank.data.remote.model.datalink.response

import com.ecospend.paybybank.data.remote.model.PayByBankNotificationOptionsRequest
import com.ecospend.paybybank.data.remote.model.datalink.DatalinkOptions
import com.ecospend.paybybank.data.remote.model.datalink.FinancialReport
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DatalinkGetResponse(

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of data access process.
     */
    @SerializedName("redirect_url")
    val redirectURL: String? = null,

    /**
     * If you are providing our Payment service to your own business clients (merchants), then you should set the Id of your merchant.
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
     * Permissions that determine which data is fetched.
     */
    val permissions: List<ConsentPermission>? = null,

    @SerializedName("datalink_options")
    val datalinkOptions: DatalinkOptions? = null,

    @SerializedName("notification_options")
    val notificationOptions: PayByBankNotificationOptionsRequest? = null,

    @SerializedName("financial_report")
    val financialReport: FinancialReport? = null,

    @SerializedName("datalink")
    val datalink: DatalinkModel? = null,

    val consents: List<ConsentModel>? = null
) : Serializable

data class ConsentModel(

    /**
     * Id of the account access correspanding Gateway account access consent reference
     */
    @SerializedName("consent_id")
    val consentId: String?,

    val status: ConsentStatus?,

    /**
     * Date of the consent creation
     */
    @SerializedName("date_created")
    val dateCreated: String?,

    /**
     * The Id assigned by the Bank for the consent
     */
    @SerializedName("bank_reference_id")
    val bankReferenceId: String?,

    /**
     * The date indicating when consent will be expired.
     */
    @SerializedName("consent_expiry_date")
    val consentExpiryDate: String?,

    /**
     * Unique identification string assigned to the bank by our system.
     */
    @SerializedName("bank_id")
    val bankId: String?,

    /**
     * The date indicating when consent will end.
     */
    @SerializedName("consent_end_date")
    val consentEndDate: String?
) : Serializable

enum class ConsentPermission(val value: String) : Serializable {
    @SerializedName("Account")
    Account("Account"),

    @SerializedName("Balance")
    Balance("Balance"),

    @SerializedName("Transactions")
    Transactions("Transactions"),

    @SerializedName("DirectDebits")
    DirectDebits("DirectDebits"),

    @SerializedName("StandingOrders")
    StandingOrders("StandingOrders"),

    @SerializedName("Parties")
    Parties("Parties"),

    @SerializedName("ScheduledPayments")
    ScheduledPayments("ScheduledPayments"),

    @SerializedName("Statements")
    Statements("Statements")
}

enum class ConsentStatus(val value: String) : Serializable {
    @SerializedName("Initial")
    Initial("Initial"),

    @SerializedName("Statements")
    AwaitingAuthorization("AwaitingAuthorization"),

    @SerializedName("Statements")
    Authorised("Authorised"),

    @SerializedName("Statements")
    Active("Active"),

    @SerializedName("Statements")
    Canceled("Canceled"),

    @SerializedName("Statements")
    Failed("Failed"),

    @SerializedName("Statements")
    Abandoned("Abandoned"),

    @SerializedName("Statements")
    Revoked("Revoked"),

    @SerializedName("Statements")
    Expired("Expired"),

    @SerializedName("Statements")
    RevocationPending("RevocationPending")
}

data class DatalinkModel(

    /**
     * A system assigned unique identification for the Datalink.
     * This value is also a part of the URL.
     */
    @SerializedName("unique_id")
    val uniqueID: String? = null,

    /**
     * Unique Datalink URL that you will need to redirect PSU in order the data access consent to proceed.
     */
    val url: String? = null,

    /**
     * Base64 encoded QRCode image data that represents Datalink URL.
     */
    @SerializedName("qr_code")
    val qrCode: String? = null,

    /**
     * Expiry date of the link.
     */
    @SerializedName("expire_date")
    val expireDate: String? = null,
) : Serializable
