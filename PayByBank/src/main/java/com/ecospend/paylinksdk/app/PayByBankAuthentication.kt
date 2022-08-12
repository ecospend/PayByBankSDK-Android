package com.ecospend.paylinksdk.app

// Configuration for authentication to endpoints.
sealed class PayByBankAuthentication {

    // Client Credentials Flow.
    //
    // - Note: The Client ID is a public identifier of your application. The Client Secret is confidential and should only be used to authenticate your application and make requests to PayByBank's APIs. For more details, please look at [Onboarding](https://docs.ecospend.com/api/intro/#section/Onboarding) documentation.
    //
    // - Parameters:
    //     - clientID: The **Client ID** is created by Ecospend when your organization is registered with us.
    //     - clientSecret: The **Client Secret** is a security key that your administrator should create from the Management Console. This is not visible to or accessible  by the Ecospend team. Therefore, you should store it safely.
    data class ClientCredentials(var clientID: String, var clientSecret: String) : PayByBankAuthentication()

    // Token-Based Authentication.
    //
    // - Note: Token-based authentication is a protocol which allows users to verify their identity, and in return receive a unique access token. During the life of the token, users access the Ecospend Gateway APIs. For more details, please look at [Get Access Token](https://docs.ecospend.com/api/intro/#tag/Get-Access-Token) documentation.
    //
    // - Parameters:
    //     - accessToken: The **Access Token** is required for all subsequent requests to the API. You should keep it safe and secure during its lifetime. The lifetime is configurable.
    //     - type: Type of token provided. Defaults to "Bearer"
    data class Token(var accessToken: String, var type: String = "Bearer") : PayByBankAuthentication()
}
