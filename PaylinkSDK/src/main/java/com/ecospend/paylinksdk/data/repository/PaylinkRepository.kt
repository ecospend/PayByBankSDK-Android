package com.ecospend.paylinksdk.data.repository

import com.ecospend.paylinksdk.app.PayByBankState
import com.ecospend.paylinksdk.data.remote.api.PaylinkAPI
import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkCreateRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkDeleteRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkGetRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkPaymentGetRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.response.PaylinkCreateResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.response.PaylinkGetResponse
import com.ecospend.paylinksdk.data.remote.model.paylink.response.PaylinkPaymentGetResponse
import com.ecospend.paylinksdk.shared.Config

interface PaylinkRepository {
    suspend fun createPaylink(model: PaylinkCreateRequest): PaylinkCreateResponse?
    suspend fun getPaylink(model: PaylinkGetRequest): PaylinkGetResponse?
    suspend fun deletePaylink(model: PaylinkDeleteRequest): Boolean?
    suspend fun getPayments(model: PaylinkPaymentGetRequest): List<PaylinkPaymentGetResponse>?
}

class PaylinkRepositoryImpl(
    private var paylinkAPI: PaylinkAPI
) : PaylinkRepository {
    override suspend fun createPaylink(model: PaylinkCreateRequest): PaylinkCreateResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.paylinkUrl
        return paylinkAPI.createPaylink(model = model)
    }

    override suspend fun getPaylink(model: PaylinkGetRequest): PaylinkGetResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.paylinkUrl
        return paylinkAPI.getPaylink(paylinkID = model.paylinkID)
    }

    override suspend fun deletePaylink(model: PaylinkDeleteRequest): Boolean? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.paylinkUrl
        return paylinkAPI.deletePaylink(paylinkID = model.paylinkID)
    }

    override suspend fun getPayments(model: PaylinkPaymentGetRequest): List<PaylinkPaymentGetResponse>? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.paylinkUrl
        return paylinkAPI.getPayments(paylinkID = model.paylinkID)
    }
}
