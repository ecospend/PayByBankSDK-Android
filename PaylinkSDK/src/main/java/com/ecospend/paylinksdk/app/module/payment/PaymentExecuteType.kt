package com.ecospend.paylinksdk.app.module.payment

import com.ecospend.paylinksdk.data.remote.model.payment.request.PaymentCreateRequest

sealed class PaymentExecuteType {
    class Open(val id: String) : PaymentExecuteType()
    class Initiate(val request: PaymentCreateRequest) : PaymentExecuteType()
}
