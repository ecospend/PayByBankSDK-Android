package com.ecospend.paybybank.di.module

import com.ecospend.paybybank.app.module.bulkPayment.BulkPayment
import com.ecospend.paybybank.app.module.datalink.Datalink
import com.ecospend.paybybank.app.module.frPayment.FrPayment
import com.ecospend.paybybank.app.module.paylink.Paylink
import com.ecospend.paybybank.app.module.payment.Payment
import com.ecospend.paybybank.app.module.vrplink.VRPlink
import com.ecospend.paybybank.di.core.EcoDi

object PayByBankModule

fun PayByBankModule.inject() {

    /** Provides: Paylink */
    EcoDi.provide {
        Paylink(
            iamRepository = EcoDi.inject(),
            paylinkRepository = EcoDi.inject()
        )
    }

    /** Provides: Datalink */
    EcoDi.provide {
        Datalink(
            iamRepository = EcoDi.inject(),
            datalinkRepository = EcoDi.inject()
        )
    }

    /** Provides: FrPayment */
    EcoDi.provide {
        FrPayment(
            iamRepository = EcoDi.inject(),
            frPaymentRepository = EcoDi.inject()
        )
    }

    /** Provides: Payment */
    EcoDi.provide {
        Payment(
            iamRepository = EcoDi.inject(),
            paymentRepository = EcoDi.inject()
        )
    }

    /** Provides: VRPlink */
    EcoDi.provide {
        VRPlink(
            iamRepository = EcoDi.inject(),
            vrPlinkRepository = EcoDi.inject()
        )
    }

    /** Provides: BulkPayment */
    EcoDi.provide {
        BulkPayment(
            iamRepository = EcoDi.inject(),
            bulkPaymentRepository = EcoDi.inject()
        )
    }
}
