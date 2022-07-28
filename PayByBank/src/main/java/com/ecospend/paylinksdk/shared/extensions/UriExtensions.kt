package com.ecospend.paybybank.shared.extensions

import android.net.Uri

val Uri.queryParameters: Map<String, String?>
    get() = queryParameterNames.associateWith { getQueryParameter(it) }
