package com.ecospend.paylinksdk.data.remote.model.paylink.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class IamTokenRequest(

    @SerializedName("client_id")
    val clientID: String,

    @SerializedName("client_secret")
    val clientSecret: String,

    @SerializedName("grant_type")
    val grantType: String? = "client_credentials",

    val scope: String? = "px01.ecospend.pis.sandbox"
) : Serializable
