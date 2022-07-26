package com.ecospend.paylinksdk.data.remote.model.payment.response

import com.google.gson.annotations.SerializedName

data class PaymentListResponse(
    val data: List<PaymentGetResponse>?,
    val meta: PaymentMetaResponse?
)

data class PaymentMetaResponse(
    @SerializedName("total_count")
    val totalCount: Int?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("current_page")
    val currentPage: Int?
)
