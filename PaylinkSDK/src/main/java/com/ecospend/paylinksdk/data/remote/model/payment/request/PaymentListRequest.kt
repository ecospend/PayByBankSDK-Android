package com.ecospend.paylinksdk.data.remote.model.payment.request

import com.ecospend.paylinksdk.data.remote.model.payment.PaymentType
import com.google.gson.annotations.SerializedName

data class PaymentListRequest(

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
     * Filter results that has been created after the `startDate` in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("start_date")
    val startDate: String?,

    /**
     * Filter results that has been created before the `endDate` in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    @SerializedName("end_date")
    val endDate: String?,

    /**
     * Filter results by `paymentType`.
     * Note: Enum: "Auto" "Domestic" "DomesticScheduled" "International" "InternationalScheduled"
     */
    @SerializedName("payment_type")
    val paymentType: PaymentType?,

    /**
     * Paging number for query results exceeding result count limit for a single response.
     * Note: Default: 1
     */
    val page: Int?
)
