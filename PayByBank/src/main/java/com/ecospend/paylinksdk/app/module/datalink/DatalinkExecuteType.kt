package com.ecospend.paybybank.app.module.datalink

import com.ecospend.paybybank.data.remote.model.datalink.request.DatalinkCreateRequest

sealed class DatalinkExecuteType {
    class Open(val uniqueID: String) : DatalinkExecuteType()
    class OpenUrl(val url: String) : DatalinkExecuteType()
    class Initiate(val datalinkCreateRequest: DatalinkCreateRequest) : DatalinkExecuteType()
}
