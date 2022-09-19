package com.ecospend.paybybank.data.remote.api

import com.ecospend.paybybank.data.remote.model.paylink.response.IamTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IamAPI {

    @FormUrlEncoded
    suspend fun connect(
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?,
        @Field("grant_type") grantType: String = "client_credentials"
        // @Field("scope") scope: String = "px01.ecospend.pis.sandbox"
    ): IamTokenResponse?
}
