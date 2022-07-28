package com.ecospend.paybybank.app

import com.ecospend.paybybank.data.remote.model.paylink.response.IamTokenResponse

object PayByBankState {

    object Config {
        var environment = PayByBankEnvironment.Sandbox
        var clientID: String? = null
        var clientSecret: String? = null
    }

    object Network {
        var tokenResponse: IamTokenResponse? = null
    }
}
