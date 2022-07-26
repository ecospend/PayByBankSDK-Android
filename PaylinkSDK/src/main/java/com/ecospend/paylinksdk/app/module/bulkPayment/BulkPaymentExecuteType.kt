package com.ecospend.paylinksdk.app.module.bulkPayment

import com.ecospend.paylinksdk.data.remote.model.bulkPayment.BulkPaymentCreateRequest

sealed class BulkPaymentExecuteType {
    class Open(val uniqueID: String) : BulkPaymentExecuteType()
    class Initiate(val request: BulkPaymentCreateRequest) : BulkPaymentExecuteType()
}
