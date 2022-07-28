package com.ecospend.paybybank.data.repository

import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.api.VRPlinkAPI
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkCreateRequest
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkDeleteRequest
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkGetRecordsRequest
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkGetRequest
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkCreateResponse
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkGetRecordsResponse
import com.ecospend.paybybank.data.remote.model.vrplink.response.VRPlinkGetResponse
import com.ecospend.paybybank.shared.Config

interface VRPlinkRepository {
    suspend fun createVRPlink(model: VRPlinkCreateRequest): VRPlinkCreateResponse?
    suspend fun getVRPlink(model: VRPlinkGetRequest): VRPlinkGetResponse?
    suspend fun deleteVRPlink(model: VRPlinkDeleteRequest): Boolean?
    suspend fun getVRPlinkRecords(model: VRPlinkGetRecordsRequest): List<VRPlinkGetRecordsResponse>?
}

class VRPlinkRepositoryImpl(
    private val VRPlinkAPI: VRPlinkAPI
) : VRPlinkRepository {
    override suspend fun createVRPlink(model: VRPlinkCreateRequest): VRPlinkCreateResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.vrplinkUrl
        return VRPlinkAPI.createVRPlink(model = model)
    }

    override suspend fun getVRPlink(model: VRPlinkGetRequest): VRPlinkGetResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.vrplinkUrl
        return VRPlinkAPI.getVRPlink(uniqueID = model.uniqueID)
    }

    override suspend fun deleteVRPlink(model: VRPlinkDeleteRequest): Boolean? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.vrplinkUrl
        return VRPlinkAPI.deleteVRPlink(uniqueID = model.uniqueID)
    }

    override suspend fun getVRPlinkRecords(model: VRPlinkGetRecordsRequest): List<VRPlinkGetRecordsResponse>? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.vrplinkUrl
        return VRPlinkAPI.getVRPlinkRecords(uniqueID = model.uniqueID)
    }
}
