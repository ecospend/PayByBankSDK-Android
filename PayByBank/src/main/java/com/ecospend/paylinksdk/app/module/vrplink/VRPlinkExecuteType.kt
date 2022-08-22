package com.ecospend.paybybank.app.module.vrplink

import com.ecospend.paybybank.app.module.paylink.PaylinkExecuteType
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkCreateRequest

sealed class VRPlinkExecuteType {
    class Open(val id: String) : VRPlinkExecuteType()
    class OpenUrl(val url: String) : VRPlinkExecuteType()
    class Initiate(val request: VRPlinkCreateRequest) : VRPlinkExecuteType()
}
