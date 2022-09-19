package com.ecospend.paybybank.data.remote.api

import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentGetResponse
import retrofit2.http.Body
import retrofit2.http.Path

interface BulkPaymentAPI {

    suspend fun createBulkPayment(
        @Body model: BulkPaymentCreateRequest
    ): BulkPaymentCreateResponse?

    suspend fun getBulkPayment(
        @Path("uniqueID") uniqueID: String
    ): BulkPaymentGetResponse?

    suspend fun deleteBulkPayment(
        @Path("uniqueID") uniqueID: String
    ): Boolean?
}
