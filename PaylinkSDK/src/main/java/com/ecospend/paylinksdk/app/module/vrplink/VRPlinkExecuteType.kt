package com.ecospend.paylinksdk.app.module.vrplink

import com.ecospend.paylinksdk.data.remote.model.vrplink.request.VRPlinkCreateRequest

sealed class VRPlinkExecuteType {
    class Open(val id: String) : VRPlinkExecuteType()
    class Initiate(val request: VRPlinkCreateRequest) : VRPlinkExecuteType()
}
