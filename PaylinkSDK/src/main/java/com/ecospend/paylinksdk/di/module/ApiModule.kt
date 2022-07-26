package com.ecospend.paylinksdk.di.module

import com.ecospend.paylinksdk.data.remote.api.BulkPaymentAPI
import com.ecospend.paylinksdk.data.remote.api.DatalinkAPI
import com.ecospend.paylinksdk.data.remote.api.FrPaymentAPI
import com.ecospend.paylinksdk.data.remote.api.IamAPI
import com.ecospend.paylinksdk.data.remote.api.PaylinkAPI
import com.ecospend.paylinksdk.data.remote.api.PaymentAPI
import com.ecospend.paylinksdk.data.remote.api.VRPlinkAPI
import com.ecospend.paylinksdk.di.core.EcoDi
import retrofit2.Retrofit

object ApiModule

fun ApiModule.inject() {

    /** Provides: IamApi */
    EcoDi.provide {
        val retrofit = EcoDi.inject<Retrofit>()
        retrofit.create(IamAPI::class.java)
    }

    /** Provides: PaylinkAPI */
    EcoDi.provide {
        val retrofit = EcoDi.inject<Retrofit>()
        retrofit.create(PaylinkAPI::class.java)
    }

    /** Provides: DatalinkAPI */
    EcoDi.provide {
        val retrofit = EcoDi.inject<Retrofit>()
        retrofit.create(DatalinkAPI::class.java)
    }

    /** Provides: FrPaymentAPI */
    EcoDi.provide {
        val retrofit = EcoDi.inject<Retrofit>()
        retrofit.create(FrPaymentAPI::class.java)
    }

    /** Provides: PaymentAPI */
    EcoDi.provide {
        val retrofit = EcoDi.inject<Retrofit>()
        retrofit.create(PaymentAPI::class.java)
    }

    /** Provides: VRPlinkAPI */
    EcoDi.provide {
        val retrofit = EcoDi.inject<Retrofit>()
        retrofit.create(VRPlinkAPI::class.java)
    }

    /** Provides: BulkPaymentAPI */
    EcoDi.provide {
        val retrofit = EcoDi.inject<Retrofit>()
        retrofit.create(BulkPaymentAPI::class.java)
    }
}
