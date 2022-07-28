package com.ecospend.paybybank.app

import com.ecospend.paybybank.app.module.bulkPayment.BulkPayment
import com.ecospend.paybybank.app.module.datalink.Datalink
import com.ecospend.paybybank.app.module.frPayment.FrPayment
import com.ecospend.paybybank.app.module.paylink.Paylink
import com.ecospend.paybybank.app.module.payment.Payment
import com.ecospend.paybybank.app.module.vrplink.VRPlink
import com.ecospend.paybybank.di.AppDI
import com.ecospend.paybybank.di.clear
import com.ecospend.paybybank.di.core.EcoDi
import com.ecospend.paybybank.di.setup

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
