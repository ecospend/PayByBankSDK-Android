package com.ecospend.paybybank.app.module.paylink

import com.ecospend.paybybank.data.remote.model.paylink.request.PaylinkCreateRequest

sealed class PaylinkExecuteType {
    class Open(val paylinkID: String) : PaylinkExecuteType()
    class Initiate(val paylinkCreateRequest: PaylinkCreateRequest) : PaylinkExecuteType()
}
