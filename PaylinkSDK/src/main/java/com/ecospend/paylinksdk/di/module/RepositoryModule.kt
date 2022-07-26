package com.ecospend.paylinksdk.di.module

import com.ecospend.paylinksdk.data.remote.api.BulkPaymentAPI
import com.ecospend.paylinksdk.data.remote.api.DatalinkAPI
import com.ecospend.paylinksdk.data.remote.api.FrPaymentAPI
import com.ecospend.paylinksdk.data.remote.api.IamAPI
import com.ecospend.paylinksdk.data.remote.api.PaylinkAPI
import com.ecospend.paylinksdk.data.remote.api.PaymentAPI
import com.ecospend.paylinksdk.data.remote.api.VRPlinkAPI
import com.ecospend.paylinksdk.data.repository.BulkPaymentRepository
import com.ecospend.paylinksdk.data.repository.BulkPaymentRepositoryImpl
import com.ecospend.paylinksdk.data.repository.DatalinkRepository
import com.ecospend.paylinksdk.data.repository.DatalinkRepositoryImpl
import com.ecospend.paylinksdk.data.repository.FrPaymentRepository
import com.ecospend.paylinksdk.data.repository.FrPaymentRepositoryImpl
import com.ecospend.paylinksdk.data.repository.IamRepository
import com.ecospend.paylinksdk.data.repository.IamRepositoryImpl
import com.ecospend.paylinksdk.data.repository.PaylinkRepository
import com.ecospend.paylinksdk.data.repository.PaylinkRepositoryImpl
import com.ecospend.paylinksdk.data.repository.PaymentRepository
import com.ecospend.paylinksdk.data.repository.PaymentRepositoyImpl
import com.ecospend.paylinksdk.data.repository.VRPlinkRepository
import com.ecospend.paylinksdk.data.repository.VRPlinkRepositoryImpl
import com.ecospend.paylinksdk.di.core.EcoDi

object RepositoryModule

fun RepositoryModule.inject() {

    /** Provides: IamRepository */
    EcoDi.provide<IamRepository> {
        val iamApi = EcoDi.inject<IamAPI>()
        IamRepositoryImpl(iamApi)
    }

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
