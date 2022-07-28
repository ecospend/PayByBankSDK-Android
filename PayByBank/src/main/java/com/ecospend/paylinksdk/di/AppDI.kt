package com.ecospend.paybybank.di

import com.ecospend.paybybank.di.core.EcoDi
import com.ecospend.paybybank.di.module.ApiModule
import com.ecospend.paybybank.di.module.NetworkModule
import com.ecospend.paybybank.di.module.PayByBankModule
import com.ecospend.paybybank.di.module.RepositoryModule
import com.ecospend.paybybank.di.module.inject

object AppDI

fun AppDI.setup() {
    NetworkModule.inject()
    ApiModule.inject()
    RepositoryModule.inject()
    PayByBankModule.inject()
}

fun AppDI.reset() {
    EcoDi.reset()
}

fun AppDI.clear() {
    EcoDi.clear()
}
