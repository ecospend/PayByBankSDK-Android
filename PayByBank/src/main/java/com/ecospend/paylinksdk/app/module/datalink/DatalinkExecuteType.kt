package com.ecospend.paybybank.app.module.datalink

sealed class DatalinkExecuteType {
    class OpenWithUrl(val uniqueID: String, val url: String, val redirectUrl: String) : DatalinkExecuteType()
}
