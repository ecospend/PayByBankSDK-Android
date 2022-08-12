package com.ecospend.paybybank.app

import com.ecospend.paybybank.data.remote.model.paylink.response.IamTokenResponse
import com.ecospend.paylinksdk.app.PayByBankAuthentication

object PayByBankState {

    object Config {
        var environment = PayByBankEnvironment.Sandbox
        var authentication: PayByBankAuthentication? = null
    }

    object Network {
        var tokenResponse: IamTokenResponse? = null
    }
}
