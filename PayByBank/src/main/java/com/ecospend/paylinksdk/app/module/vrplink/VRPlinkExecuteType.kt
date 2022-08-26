package com.ecospend.paybybank.app.module.vrplink

import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkCreateRequest

sealed class VRPlinkExecuteType {
    class Open(val id: String) : VRPlinkExecuteType()
    class OpenUrl(val url: String, val redirectUrl: String) : VRPlinkExecuteType()
    class Initiate(val request: VRPlinkCreateRequest) : VRPlinkExecuteType()
}
