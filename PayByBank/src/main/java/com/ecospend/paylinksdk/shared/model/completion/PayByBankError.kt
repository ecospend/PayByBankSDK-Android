package com.ecospend.paybybank.shared.model.completion

sealed class PayByBankError : Exception() {
    class ActivityError(val error: String?) : PayByBankError()
    class Unknown(val error: String?) : PayByBankError()
    object NotConfigured : PayByBankError()
    class WrongPaylink(val error: String?) : PayByBankError()
    class Network(val networkError: NetworkError) : PayByBankError()
}

sealed class NetworkError : Exception() {
    object NoData : NetworkError()
    object InvalidResponse : NetworkError()
    class BadRequest(val code: Int, val error: String?) : NetworkError()
    class ServerError(val code: Int, val error: String?) : NetworkError()
    class ParseError(val code: Int, val error: String?) : NetworkError()
    class Unknown(val error: String?) : NetworkError()
}
