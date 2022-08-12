package com.ecospend.paylinkmobilesdk.app

import android.app.Application
import com.ecospend.paybybank.app.PayByBank
import com.ecospend.paybybank.app.PayByBankEnvironment
import com.ecospend.paylinksdk.app.PayByBankAuthentication

class PaylinkMobileSDK : Application() {
    override fun onCreate() {
        super.onCreate()
        PayByBank.configure(
            environment = PayByBankEnvironment.Sandbox,
            authentication = PayByBankAuthentication.ClientCredentials("*********", "***********")
        )
    }
}
