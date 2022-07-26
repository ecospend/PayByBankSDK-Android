package com.ecospend.paylinksdk.di

import com.ecospend.paylinksdk.di.core.EcoDi
import com.ecospend.paylinksdk.di.module.ApiModule
import com.ecospend.paylinksdk.di.module.NetworkModule
import com.ecospend.paylinksdk.di.module.PayByBankModule
import com.ecospend.paylinksdk.di.module.RepositoryModule
import com.ecospend.paylinksdk.di.module.inject

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
