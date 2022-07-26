package com.ecospend.paylinksdk.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaylinkPaymentOptions(

    /**
     * Payment rails information of the paylink.
     */
    @SerializedName("payment_rails")
    val paymentRails: String? = null,

    /**
     * Set true, if you would like to get back the debtor's account information that the payment is made from.
     * Defaults to true.
     */
    @SerializedName("get_refund_info")
    val getRefundInfo: Boolean? = null,

    /**
     * Specifies that the payment is for payout operation.
     * Default is false.
     */
    @SerializedName("for_payout")
    val forPayout: Boolean? = null
) : Serializable
