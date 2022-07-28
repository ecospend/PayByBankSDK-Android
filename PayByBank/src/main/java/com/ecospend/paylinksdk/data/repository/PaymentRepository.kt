package com.ecospend.paybybank.data.repository

import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.api.PaymentAPI
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentCheckURLRequest
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentCreateRefundRequest
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentGetRequest
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentListRequest
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentCheckURLResponse
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentGetResponse
import com.ecospend.paybybank.data.remote.model.payment.response.PaymentListResponse
import com.ecospend.paybybank.shared.Config

interface PaymentRepository {
    suspend fun createPayment(request: PaymentCreateRequest): PaymentCreateResponse?
    suspend fun getPayment(request: PaymentGetRequest): PaymentGetResponse?
    suspend fun listPayments(request: PaymentListRequest): PaymentListResponse?
    suspend fun checkPaymentURL(request: PaymentCheckURLRequest): PaymentCheckURLResponse?
    suspend fun createRefund(request: PaymentCreateRefundRequest): PaymentCreateResponse?
}

class PaymentRepositoyImpl(
    private val paymentAPI: PaymentAPI
) : PaymentRepository {
    override suspend fun createPayment(request: PaymentCreateRequest): PaymentCreateResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.paymentUrl
        return paymentAPI.createPayment(model = request)
    }

    override suspend fun getPayment(request: PaymentGetRequest): PaymentGetResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.paymentUrl
        return paymentAPI.getPayment(id = request.id)
    }

    override suspend fun listPayments(request: PaymentListRequest): PaymentListResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.paymentUrl
        return paymentAPI.listPayments(model = request)
    }

    override suspend fun checkPaymentURL(request: PaymentCheckURLRequest): PaymentCheckURLResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.paymentUrl
        return paymentAPI.checkPaymentURL(id = request.id)
    }

    override suspend fun createRefund(request: PaymentCreateRefundRequest): PaymentCreateResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.paymentUrl
        return paymentAPI.createRefund(id = request.id)
    }
}
