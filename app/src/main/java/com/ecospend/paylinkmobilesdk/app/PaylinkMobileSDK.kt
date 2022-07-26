package com.ecospend.paylinkmobilesdk.app

import android.app.Application
import com.ecospend.paylinksdk.app.PayByBank
import com.ecospend.paylinksdk.app.PayByBankEnvironment

class PaylinkMobileSDK : Application() {
    override fun onCreate() {
        super.onCreate()
        PayByBank.configure(
            clientId = "910162c0-a0e6-40b8-b66d-f6a9d56bee0f",
            clientSecret = "e5cfcc4b22b7ef8059b53bb54c9dca15f28de13bd90b4567a679523ab86cd8d0",
            environment = PayByBankEnvironment.Sandbox
        )
    }
}
