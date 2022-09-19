package com.ecospend.paybybank.data.remote.api

import com.ecospend.paybybank.data.remote.model.payment.request.PaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentListRequest
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentCheckURLResponse
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentGetResponse
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentAPI {

    suspend fun createPayment(
        @Body model: PaymentCreateRequest
    ): PaymentCreateResponse?

    suspend fun getPayment(
        @Path("id") id: String
    ): PaymentGetResponse?

    suspend fun listPayments(
        @Body model: PaymentListRequest
    ): PaymentListResponse?

    suspend fun checkPaymentURL(
        @Path("id") id: String
    ): PaymentCheckURLResponse?

    suspend fun createRefund(
        @Path("id") id: String
    ): PaymentCreateResponse?
}
