package com.ecospend.paybybank.app.module.vrplink

sealed class VRPlinkExecuteType {
    class OpenWithUrl(val uniqueID: String, val url: String, val redirectUrl: String) : VRPlinkExecuteType()
}
