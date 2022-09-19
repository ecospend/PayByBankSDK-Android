package com.ecospend.paybybank.app.module.bulkPayment

sealed class BulkPaymentExecuteType {
    class OpenWithUrl(val uniqueID: String, val url: String, val redirectUrl: String) : BulkPaymentExecuteType()
}
