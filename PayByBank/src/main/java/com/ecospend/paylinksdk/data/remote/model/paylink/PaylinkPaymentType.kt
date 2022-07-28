package com.ecospend.paybybank.data.remote.model.paylink

import java.io.Serializable

enum class PaylinkPaymentType : Serializable {
    Auto,
    Domestic,
    DomesticScheduled,
    International,
    InternationalScheduled
}
