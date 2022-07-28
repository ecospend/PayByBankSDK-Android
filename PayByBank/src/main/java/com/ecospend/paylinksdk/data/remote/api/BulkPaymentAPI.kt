package com.ecospend.paybybank.data.remote.api

import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateRequest
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentCreateResponse
import com.ecospend.paybybank.data.remote.model.bulkPayment.BulkPaymentGetResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BulkPaymentAPI {

    @POST("bulk-payment-paylinks")
    suspend fun createBulkPayment(
        @Body model: BulkPaymentCreateRequest
    ): BulkPaymentCreateResponse?

    @GET("bulk-payment-paylinks/{uniqueID}")
    suspend fun getBulkPayment(
        @Path("uniqueID") uniqueID: String
    ): BulkPaymentGetResponse?

    @DELETE("bulk-payment-paylinks/{uniqueID}")
    suspend fun deleteBulkPayment(
        @Path("uniqueID") uniqueID: String
    ): Boolean?
}
