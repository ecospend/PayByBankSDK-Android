package com.ecospend.paylinksdk.app.module.frPayment

import com.ecospend.paylinksdk.data.remote.model.frPayment.FrPaymentCreateRequest

sealed class FrPaymentExecuteType {
    class Open(val uniqueID: String) : FrPaymentExecuteType()
    class Initiate(val frPaymentCreateRequest: FrPaymentCreateRequest) : FrPaymentExecuteType()
}
