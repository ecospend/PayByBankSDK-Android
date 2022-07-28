package com.ecospend.paybybank.data.remote.model.vrplink.response

import com.ecospend.paybybank.data.remote.model.paylink.PayByBankAccountResponse
import com.ecospend.paybybank.data.remote.model.vrplink.VRPType
import com.google.gson.annotations.SerializedName

data class VRPlinkGetRecordsResponse(

    /**
     * Paylink Id of the payment
     */
    @SerializedName("unique_id")
    val uniqueID: String?,

    /**
     * Client Id of the payment
     */
    @SerializedName("client_id")
    val clientID: String?,

    /**
     * Tenant id of the A,PI consumer
     */
    @SerializedName("tenant_id")
    val tenantID: Int?,

    /**
     * A system assigned unique identification for the payment.
     * You may need to use this id to query payments or initiate a refund.
     */
    @SerializedName("bank_reference_id")
    val id: String?,

    /**
     * An identification number for the payment that is assigned by the bank. Can have different formats for each bank.
     */
    @SerializedName("bank_reference_id")
    val bankReferenceID: String?,

    /**
     * A unique and one time use only URL from the PSU’s bank.
     * You will need to redirect the PSU to this link for them to authorise a payment.
     */
    @SerializedName("date_created")
    val dateCreated: String?,

    /**
     * Unique identification string assigned to the bank by our system.
     */
    @SerializedName("bank_id")
    val bankID: String?,

    /**
     * Status of the VRP flow
     * Enum: "Initial" "AwaitingAuthorization" "Authorised" "Revoked" "Expired" "Canceled" "Failed" "Rejected" "Abandoned"
     */
    @SerializedName("unique_id")
    val status: String?,

    /**
     * The description that you provided with the request (if any).
     */
    @SerializedName("unique_id")
    val description: String?,

    /**
     * The reference that you provided with the request.
     */
    @SerializedName("unique_id")
    val reference: String?,

    /**
     * The merchantID that you provided with the request (if any).
     */
    @SerializedName("merchant_id")
    val merchantID: String?,

    /**
     * The merchantUserID that you provided with the request (if any).
     */
    @SerializedName("merchant_user_id")
    val merchantUserID: String?,

    /**
     * The URL of the Tenant that the PSU will be redirected at the end of payment process.
     */
    @SerializedName("redirect_url")
    val redirectURL: String?,

    /**
     * It determines which type of payment operation will be executed by the Gateway.
     * Enum: "Sweeping" "Vrp"
     */
    val type: VRPType?,

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
     * Failure reason of the failed payments.
     */
    @SerializedName("failure_message")
    val failureMessage: String?,

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
     * If you are set true, no redirect after vrp.
     */
    @SerializedName("dont_redirect")
    val dontRedirect: Boolean?,
)

enum class VRPStatus {
    /**
     * Reques' is made but a response is not provided yet.
     */
    @SerializedName("Initial")
    Initial,

    /**
     * A response, including bank's vrp URL is returned and the PSU is expected to authorise the consent.
     */
    @SerializedName("AwaitingAuthorization")
    AwaitingAuthorization,

    /**
     * The PSU has authorized the vrp from their banking system.
     */
    @SerializedName("Authorised")
    Authorised,

    /**
     * The PSU has revoked the vrp.
     */
    @SerializedName("Revoked")
    Revoked,

    /**
     * VRP has expired.
     */
    @SerializedName("Expired")
    Expired,

    /**
     * The PSU has cancelled the VRP flow.
     */
    @SerializedName("Canceled")
    Canceled,

    /**
     * VRP flow has failed due to an error.
     */
    @SerializedName("Failed")
    Failed,

    /**
     * Bank has rejected the VRP.
     */
    @SerializedName("Rejected")
    Rejected,

    /**
     * The PSU has deserted the vrp journey prior to being redirected back from the bank.
     */
    @SerializedName("Abandoned")
    Abandoned,
}
