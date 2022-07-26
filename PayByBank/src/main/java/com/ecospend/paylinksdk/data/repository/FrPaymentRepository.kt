package com.ecospend.paybybank.data.repository

import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.api.FrPaymentAPI
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentDeleteRequest
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentGetRequest
import com.ecospend.paybybank.data.remote.model.frPayment.FrPaymentGetResponse
import com.ecospend.paybybank.shared.Config

interface FrPaymentRepository {
    suspend fun createFrPayment(request: FrPaymentCreateRequest): FrPaymentCreateResponse?
    suspend fun getFrPayment(request: FrPaymentGetRequest): FrPaymentGetResponse?
    suspend fun deleteFrPayment(request: FrPaymentDeleteRequest): Boolean?
}

data class FrPaymentRepositoryImpl(
    private val frPaymentAPI: FrPaymentAPI
) : FrPaymentRepository {
    override suspend fun createFrPayment(request: FrPaymentCreateRequest): FrPaymentCreateResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.frPaymentUrl
        return frPaymentAPI.createFrPayment(model = request)
    }

    override suspend fun getFrPayment(request: FrPaymentGetRequest): FrPaymentGetResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.frPaymentUrl
        return frPaymentAPI.getFrPayment(uniqueID = request.uniqueID)
    }

    override suspend fun deleteFrPayment(request: FrPaymentDeleteRequest): Boolean? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.frPaymentUrl
        return frPaymentAPI.deleteFrPayment(uniqueID = request.uniqueID)
    }
}
