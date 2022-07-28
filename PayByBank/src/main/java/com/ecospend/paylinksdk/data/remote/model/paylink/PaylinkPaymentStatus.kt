package com.ecospend.paybybank.data.remote.model.paylink

import java.io.Serializable

enum class PaylinkPaymentStatus : Serializable {
    Initial,
    AwaitingAuthorization,
    Authorised,
    Verified,
    Completed,
    Canceled,
    Failed,
    Rejected,
    Abandoned
}
