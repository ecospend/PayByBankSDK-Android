package com.ecospend.paybybank.data.remote.model.paylink

import com.google.gson.annotations.SerializedName

class PaylinkLimitOptionsResponse(

    /**
     * Specifies if the limit is exceeded or not.
     */
    @SerializedName("limit_exceeded")
    val isLimitExceeded: Boolean? = null,

    /**
     * Maximum successfull payment count limit.
     */
    val count: Int? = null,

    /**
     * Maximum amount value for collecting payment with the paylink.
     */
    val amount: Float? = null,

    /**
     * Expire date for the paylink in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format.
     */
    val date: String? = null,
)
