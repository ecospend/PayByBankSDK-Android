package com.ecospend.paybybank.app.module.paylink

sealed class PaylinkExecuteType {
    class OpenWithUrl(val uniqueID: String, val url: String, val redirectUrl: String) : PaylinkExecuteType()
}
