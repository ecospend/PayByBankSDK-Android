package com.ecospend.paybybank.app.module.frPayment

sealed class FrPaymentExecuteType {
    class OpenWithUrl(val uniqueID: String, val url: String, val redirectUrl: String) : FrPaymentExecuteType()
}
