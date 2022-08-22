package com.ecospend.paybybank.app.module.bulkPayment

import com.ecospend.paybybank.app.module.vrplink.VRPlinkExecuteType
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateRequest

sealed class BulkPaymentExecuteType {
    class Open(val uniqueID: String) : BulkPaymentExecuteType()
    class OpenUrl(val url: String) : BulkPaymentExecuteType()
    class Initiate(val request: BulkPaymentCreateRequest) : BulkPaymentExecuteType()
}
