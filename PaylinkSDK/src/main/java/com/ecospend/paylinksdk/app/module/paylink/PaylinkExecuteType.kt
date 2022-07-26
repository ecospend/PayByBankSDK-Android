package com.ecospend.paylinksdk.app.module.paylink

import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkCreateRequest

sealed class PaylinkExecuteType {
    class Open(val paylinkID: String) : PaylinkExecuteType()
    class Initiate(val paylinkCreateRequest: PaylinkCreateRequest) : PaylinkExecuteType()
}
