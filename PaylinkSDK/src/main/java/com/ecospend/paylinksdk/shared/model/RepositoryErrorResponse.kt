package com.ecospend.paylinksdk.shared.model

import java.io.Serializable

class RepositoryErrorResponse(
    val error: RepositoryErrorType?,
    description: String?,
    details: Map<String, List<String>>?
) : Serializable {
    val localizedDescription = "1) $error \n2) $description \n " + "3) ${
    details?.map {
        "${it.key}: ${it.value.joinToString(separator = ",") { value -> value }}"
    }?.joinToString(separator = ", ") { it }}"
}

enum class RepositoryErrorType : Serializable {
    NoError,
    AuthorizationError,
    InvalidRequest,
    UnprocessableEntityError,
    NotFoundError,
    InvalidBankRequest,
    InternalServerError,
    BankServiceError,
    ExternalServerError,
    RedirectServerError
}
