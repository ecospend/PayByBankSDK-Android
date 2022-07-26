package com.ecospend.paylinksdk.data.repository

import com.ecospend.paylinksdk.app.PayByBankState
import com.ecospend.paylinksdk.data.remote.api.IamAPI
import com.ecospend.paylinksdk.data.remote.model.paylink.request.IamTokenRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.response.IamTokenResponse
import com.ecospend.paylinksdk.shared.Config

interface IamRepository {
    suspend fun connect(iamTokenRequest: IamTokenRequest): IamTokenResponse?
}

class IamRepositoryImpl(
    private var iamAPI: IamAPI
) : IamRepository {

    override suspend fun connect(iamTokenRequest: IamTokenRequest): IamTokenResponse? {
        Config.Network.apiBaseUrl = PayByBankState.Config.environment.iamUrl
        return iamAPI.connect(
            clientSecret = iamTokenRequest.clientSecret,
            clientId = iamTokenRequest.clientID
        )?.also {
            PayByBankState.Network.tokenResponse = it
        }
    }
}
