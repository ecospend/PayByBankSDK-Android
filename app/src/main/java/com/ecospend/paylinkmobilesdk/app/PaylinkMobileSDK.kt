package com.ecospend.paylinkmobilesdk.app

import android.app.Application
import com.ecospend.paybybank.app.PayByBank
import com.ecospend.paybybank.app.PayByBankEnvironment

class PaylinkMobileSDK : Application() {
    override fun onCreate() {
        super.onCreate()
        PayByBank.configure(
            environment = PayByBankEnvironment.Sandbox,
            token = "*********"
        )
    }
}
