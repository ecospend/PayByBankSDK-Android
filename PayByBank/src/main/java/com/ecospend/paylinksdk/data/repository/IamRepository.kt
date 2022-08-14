package com.ecospend.paybybank.data.repository

import com.ecospend.paybybank.app.PayByBankState
import com.ecospend.paybybank.data.remote.api.IamAPI
import com.ecospend.paybybank.data.remote.model.paylink.response.IamTokenResponse
import com.ecospend.paybybank.shared.Config
import com.ecospend.paybybank.shared.model.completion.PayByBankError
import com.ecospend.paylinksdk.app.PayByBankAuthentication

interface IamRepository {
    suspend fun connect(): IamTokenResponse?
}

class IamRepositoryImpl(
    private var iamAPI: IamAPI
) : IamRepository {

    override suspend fun connect(): IamTokenResponse? {

        Config.Network.apiBaseUrl = PayByBankState.Config.environment.iamUrl

        return when (PayByBankState.Config.authentication) {
            is PayByBankAuthentication.ClientCredentials -> iamAPI.connect(
                clientSecret = (PayByBankState.Config.authentication as PayByBankAuthentication.ClientCredentials).clientSecret,
                clientId = (PayByBankState.Config.authentication as PayByBankAuthentication.ClientCredentials).clientID
            )?.also {
                PayByBankState.Network.tokenResponse = it
            }
            is PayByBankAuthentication.Token ->
                IamTokenResponse(
                    accessToken = (PayByBankState.Config.authentication as PayByBankAuthentication.Token).accessToken,
                    tokenType = (PayByBankState.Config.authentication as PayByBankAuthentication.Token).type
                )
                    .also { PayByBankState.Network.tokenResponse = it }
            else -> {
                throw PayByBankError.NotConfigured
            }
        }
    }
}
