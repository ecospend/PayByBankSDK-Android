package com.ecospend.paylinksdk.data.remote.model.paylink

import java.io.Serializable

enum class PaylinkPaymentType : Serializable {
    Auto,
    Domestic,
    DomesticScheduled,
    International,
    InternationalScheduled
}
