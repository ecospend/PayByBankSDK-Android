package com.ecospend.paybybank.app.module.payment

import com.ecospend.paybybank.data.remote.model.payment.request.PaymentCreateRequest

sealed class PaymentExecuteType {
    class Open(val id: String) : PaymentExecuteType()
    class Initiate(val request: PaymentCreateRequest) : PaymentExecuteType()
}
