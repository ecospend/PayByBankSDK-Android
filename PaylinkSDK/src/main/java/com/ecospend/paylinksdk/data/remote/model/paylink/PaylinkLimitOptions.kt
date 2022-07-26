package com.ecospend.paylinksdk.data.remote.model.paylink

import java.io.Serializable

data class PaylinkLimitOptions(

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
    val date: String? = null
) : Serializable
