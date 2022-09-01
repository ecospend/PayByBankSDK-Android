package com.ecospend.paybybank.app.module.bulkPayment

import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateRequest

sealed class BulkPaymentExecuteType {
    class Open(val uniqueID: String) : BulkPaymentExecuteType()
    class OpenWithUrl(val uniqueID: String, val url: String, val redirectUrl: String) : BulkPaymentExecuteType()
    class Initiate(val request: BulkPaymentCreateRequest) : BulkPaymentExecuteType()
}
