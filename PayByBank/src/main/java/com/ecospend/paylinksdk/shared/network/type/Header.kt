package com.ecospend.paybybank.shared.network.type

enum class Header(val key: String, val value: String) {
    ContentType("Content-Type", Mime.Application.JSON),
    Authorization("Authorization", "Bearer %s")
}
