package com.ecospend.paybybank.app.module.frPayment

import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentCreateRequest

sealed class FrPaymentExecuteType {
    class Open(val uniqueID: String) : FrPaymentExecuteType()
    class OpenWithUrl(val uniqueID: String, val url: String, val redirectUrl: String) : FrPaymentExecuteType()
    class Initiate(val frPaymentCreateRequest: FrPaymentCreateRequest) : FrPaymentExecuteType()
}
