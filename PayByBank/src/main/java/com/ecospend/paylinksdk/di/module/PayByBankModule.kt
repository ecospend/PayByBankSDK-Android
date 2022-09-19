package com.ecospend.paybybank.di.module

import com.ecospend.paybybank.app.module.bulkPayment.BulkPayment
import com.ecospend.paybybank.app.module.datalink.Datalink
import com.ecospend.paybybank.app.module.frPayment.FrPayment
import com.ecospend.paybybank.app.module.paylink.Paylink
import com.ecospend.paybybank.app.module.vrplink.VRPlink
import com.ecospend.paybybank.di.core.EcoDi

object PayByBankModule

fun PayByBankModule.inject() {

    /** Provides: Paylink */
    EcoDi.provide {
        Paylink()
    }

    /** Provides: Datalink */
    EcoDi.provide {
        Datalink()
    }

    /** Provides: FrPayment */
    EcoDi.provide {
        FrPayment()
    }

    /** Provides: VRPlink */
    EcoDi.provide {
        VRPlink()
    }

    /** Provides: BulkPayment */
    EcoDi.provide {
        BulkPayment()
    }
}
