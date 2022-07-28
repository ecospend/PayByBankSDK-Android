package com.ecospend.paybybank.shared.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

object Coroutine {
    val job = Job()
    val coroutineScope get() = CoroutineScope(Dispatchers.IO + job)
}

fun Coroutine.cancel() {
    job.cancel()
    coroutineScope.cancel()
}
