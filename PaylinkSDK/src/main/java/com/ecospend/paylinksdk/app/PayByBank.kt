package com.ecospend.paylinksdk.app

import com.ecospend.paylinksdk.app.module.bulkPayment.BulkPayment
import com.ecospend.paylinksdk.app.module.datalink.Datalink
import com.ecospend.paylinksdk.app.module.frPayment.FrPayment
import com.ecospend.paylinksdk.app.module.paylink.Paylink
import com.ecospend.paylinksdk.app.module.payment.Payment
import com.ecospend.paylinksdk.app.module.vrplink.VRPlink
import com.ecospend.paylinksdk.di.AppDI
import com.ecospend.paylinksdk.di.clear
import com.ecospend.paylinksdk.di.core.EcoDi
import com.ecospend.paylinksdk.di.setup

object PayByBank {
    val payLink by lazy {
        handleDI()
        EcoDi.inject<Paylink>()
    }

    val datalink by lazy {
        handleDI()
        EcoDi.inject<Datalink>()
    }

    val frPayment by lazy {
        handleDI()
        EcoDi.inject<FrPayment>()
    }

    val payment by lazy {
        handleDI()
        EcoDi.inject<Payment>()
    }

    val vrplink by lazy {
        handleDI()
        EcoDi.inject<VRPlink>()
    }

    val bulkPayment by lazy {
        handleDI()
        EcoDi.inject<BulkPayment>()
    }

    fun configure(
        clientId: String,
        clientSecret: String,
        environment: PayByBankEnvironment
    ) = PayByBankState.Config
        .apply {
            this.clientID = clientId
            this.clientSecret = clientSecret
            this.environment = environment
        }

    private fun handleDI() {
        PayByBankState.Network.tokenResponse = null
        AppDI.clear()
        AppDI.setup()
    }
}
