package com.ecospend.paybybank.di.module

import com.ecospend.paybybank.data.remote.api.BulkPaymentAPI
import com.ecospend.paybybank.data.remote.api.DatalinkAPI
import com.ecospend.paybybank.data.remote.api.FrPaymentAPI
import com.ecospend.paybybank.data.remote.api.PaylinkAPI
import com.ecospend.paybybank.data.remote.api.PaymentAPI
import com.ecospend.paybybank.data.remote.api.VRPlinkAPI
import com.ecospend.paybybank.data.repository.BulkPaymentRepository
import com.ecospend.paybybank.data.repository.BulkPaymentRepositoryImpl
import com.ecospend.paybybank.data.repository.DatalinkRepository
import com.ecospend.paybybank.data.repository.DatalinkRepositoryImpl
import com.ecospend.paybybank.data.repository.FrPaymentRepository
import com.ecospend.paybybank.data.repository.FrPaymentRepositoryImpl
import com.ecospend.paybybank.data.repository.PaylinkRepository
import com.ecospend.paybybank.data.repository.PaylinkRepositoryImpl
import com.ecospend.paybybank.data.repository.PaymentRepository
import com.ecospend.paybybank.data.repository.PaymentRepositoyImpl
import com.ecospend.paybybank.data.repository.VRPlinkRepository
import com.ecospend.paybybank.data.repository.VRPlinkRepositoryImpl
import com.ecospend.paybybank.di.core.EcoDi

object RepositoryModule

fun RepositoryModule.inject() {

    /** Provides: PaylinkRepository */
    EcoDi.provide<PaylinkRepository> {
        val paylinkAPI = EcoDi.inject<PaylinkAPI>()
        PaylinkRepositoryImpl(paylinkAPI)
    }

    /** Provides: DatalinkRepository */
    EcoDi.provide<DatalinkRepository> {
        val datalinkAPI = EcoDi.inject<DatalinkAPI>()
        DatalinkRepositoryImpl(datalinkAPI)
    }

    /** Provides: FrPaymentRepository */
    EcoDi.provide<FrPaymentRepository> {
        val frPaymentAPI = EcoDi.inject<FrPaymentAPI>()
        FrPaymentRepositoryImpl(frPaymentAPI)
    }

    /** Provides: PaymentRepository */
    EcoDi.provide<PaymentRepository> {
        val paymentAPI = EcoDi.inject<PaymentAPI>()
        PaymentRepositoyImpl(paymentAPI)
    }

    /** Provides: VRPlinkRepository */
    EcoDi.provide<VRPlinkRepository> {
        val vrplinkApi = EcoDi.inject<VRPlinkAPI>()
        VRPlinkRepositoryImpl(vrplinkApi)
    }

    /** Provides: BulkPaymentRepository */
    EcoDi.provide<BulkPaymentRepository> {
        val bulkPaymentAPI = EcoDi.inject<BulkPaymentAPI>()
        BulkPaymentRepositoryImpl(bulkPaymentAPI)
    }
}
