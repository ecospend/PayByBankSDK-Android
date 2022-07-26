package com.ecospend.paylinksdk.data.remote.model.paylink.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IamTokenResponse(

    @SerializedName("access_token")
    val accessToken: String? = null,

    @SerializedName("token_type")
    val tokenType: String? = null,

    @SerializedName("expires_in")
    val expiresIn: Int? = null,

    val scope: String? = null
) : Serializable
