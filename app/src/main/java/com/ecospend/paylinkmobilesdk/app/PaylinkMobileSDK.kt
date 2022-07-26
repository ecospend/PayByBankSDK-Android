package com.ecospend.paylinkmobilesdk.app

import android.app.Application
import com.ecospend.paylinksdk.app.PayByBank
import com.ecospend.paylinksdk.app.PayByBankEnvironment

class PaylinkMobileSDK : Application() {
    override fun onCreate() {
        super.onCreate()
        PayByBank.configure(
            clientId = "*******************",
            clientSecret = "******************",
            environment = PayByBankEnvironment.Sandbox
        )
    }
}
