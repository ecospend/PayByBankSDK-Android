package com.ecospend.paybybank.data.repository

import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.api.BulkPaymentAPI
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentDeleteRequest
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentGetRequest
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentGetResponse
import com.ecospend.paybybank.shared.Config

interface BulkPaymentRepository {
    suspend fun createBulkPayment(model: BulkPaymentCreateRequest): BulkPaymentCreateResponse?
    suspend fun getBulkPayment(model: BulkPaymentGetRequest): BulkPaymentGetResponse?
    suspend fun deleteBulkPayment(model: BulkPaymentDeleteRequest): Boolean?
}

class BulkPaymentRepositoryImpl(
    private val bulkPaymentAPI: BulkPaymentAPI
) : BulkPaymentRepository {
    override suspend fun createBulkPayment(model: BulkPaymentCreateRequest): BulkPaymentCreateResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.bulkPaymentUrl
        return bulkPaymentAPI.createBulkPayment(model = model)
    }

    override suspend fun getBulkPayment(model: BulkPaymentGetRequest): BulkPaymentGetResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.bulkPaymentUrl
        return bulkPaymentAPI.getBulkPayment(uniqueID = model.uniqueID)
    }

    override suspend fun deleteBulkPayment(model: BulkPaymentDeleteRequest): Boolean? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.bulkPaymentUrl
        return bulkPaymentAPI.deleteBulkPayment(uniqueID = model.uniqueID)
    }
}
