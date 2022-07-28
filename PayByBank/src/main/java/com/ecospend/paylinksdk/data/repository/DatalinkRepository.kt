package com.ecospend.paybybank.data.repository

import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.api.DatalinkAPI
import com.ecospend.paybybank.data.remote.model.datalink.request.DatalinkCreateRequest
import com.ecospend.paybybank.data.remote.model.datalink.request.DatalinkDeleteRequest
import com.ecospend.paybybank.data.remote.model.datalink.request.DatalinkGetConsentDatalinkRequest
import com.ecospend.paybybank.data.remote.model.datalink.request.DatalinkGetRequest
import com.ecospend.paybybank.data.remote.model.datalink.response.DatalinkCreateResponse
import com.ecospend.paybybank.data.remote.model.datalink.response.DatalinkGetResponse
import com.ecospend.paybybank.shared.Config

interface DatalinkRepository {
    suspend fun createDatalink(model: DatalinkCreateRequest): DatalinkCreateResponse?
    suspend fun getDatalink(model: DatalinkGetRequest): DatalinkGetResponse?
    suspend fun deleteDatalink(model: DatalinkDeleteRequest): Boolean?
    suspend fun getDatalinkOfConsent(model: DatalinkGetConsentDatalinkRequest): DatalinkGetResponse?
}

class DatalinkRepositoryImpl(
    private var datalinkAPI: DatalinkAPI
) : DatalinkRepository {
    override suspend fun createDatalink(model: DatalinkCreateRequest): DatalinkCreateResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.datalinkUrl
        return datalinkAPI.createDatalink(model = model)
    }

    override suspend fun getDatalink(model: DatalinkGetRequest): DatalinkGetResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.datalinkUrl
        return datalinkAPI.getDatalink(uniqueID = model.uniqueID)
    }

    override suspend fun deleteDatalink(model: DatalinkDeleteRequest): Boolean? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.datalinkUrl
        return datalinkAPI.deleteDatalink(uniqueID = model.uniqueID)
    }

    override suspend fun getDatalinkOfConsent(model: DatalinkGetConsentDatalinkRequest): DatalinkGetResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.datalinkUrl
        return datalinkAPI.getDatalinkOfConsent(consentID = model.consentId)
    }
}
