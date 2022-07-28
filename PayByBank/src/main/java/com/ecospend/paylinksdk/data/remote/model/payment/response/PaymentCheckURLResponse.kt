package com.ecospend.paybybank.data.remote.model.payment.response

import com.google.gson.annotations.SerializedName

data class PaymentCheckURLResponse(
    /**
     * Indicates the current status of the `paymentURL`.
     * Note: true: the `paymentURL` is no longer valid.
     * Note: false: the `paymentURL` is available (The payment_url has a lifespan that is managed by each bank differently.
     * Even the url is not consumed it may be expired by the bank.) to use.
     */
    @SerializedName("is_consumed")
    val isConsumed: Boolean
)
