package com.ecospend.paylinksdk.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaylinkTip(

    /**
     * Payment rails information of the paylink.
     */
    @SerializedName("request_tip")
    val requestTip: Boolean? = null,

    /**
     * The Title of the Tip Request Page.
     */
    val title: String? = null,

    /**
     * The informative text on the Tip Request Page.
     */
    val text: String? = null,

    /**
     * Tip can be configured as required.
     * In this case the payer will be forced to select or enter tip amount.
     */
    @SerializedName("is_required")
    val isRequired: Boolean? = null,

    /**
     * The tip options that will be listed on the Tip Request Page.
     */
    val options: List<PaylinkTipOption>? = null
) : Serializable
