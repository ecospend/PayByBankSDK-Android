package com.ecospend.paybybank.app

import com.ecospend.paybybank.app.module.bulkPayment.BulkPayment
import com.ecospend.paybybank.app.module.datalink.Datalink
import com.ecospend.paybybank.app.module.frPayment.FrPayment
import com.ecospend.paybybank.app.module.paylink.Paylink
import com.ecospend.paybybank.app.module.payment.Payment
import com.ecospend.paybybank.app.module.vrplink.VRPlink
import com.ecospend.paybybank.di.AppDI
import com.ecospend.paybybank.di.clear
import com.ecospend.paybybank.di.core.EcoDi
import com.ecospend.paybybank.di.setup
import com.ecospend.paylinksdk.app.PayByBankAuthentication

/**
 * PayByBank SDK.
 * The Ecospend Gateway presents PayByBank SDK as an alternative and easier form of Open Banking Instant Payment solutions.
 * PayByBank SDK provides you the option of downsizing the development effort for a PIS and AIS journeys to a single SDK integration.
 * PayByBank undertakes all of interaction in the payment user journey with your branding on display.
 */
object PayByBank {

    /**
     * Paylink API
     * The Ecospend Gateway presents Paylink as an alternative and easier form of Open Banking Instant Payment solution.
     * Paylink provides you the option of downsizing the development effort for a PIS journey to a single endpoint integration.
     * Paylink undertakes all of interaction in the payment user journey with your branding on display.
     */
    val payLink by lazy {
        handleDI()
        EcoDi.inject<Paylink>()
    }

    /**
     *  Datalink API
     * Datalink is a whitelabel consent journey solution provided by Ecospend that downsizes the required implementation for the consent journey to a single endpoint integration.
     * By making a single call to /datalink endpoint you will be able to initiate the consent journey.
     */
    val datalink by lazy {
        handleDI()
        EcoDi.inject<Datalink>()
    }

    /**
     *  FrPayment (Standing Order) API
     * A Standing Order is an instruction that an account holder gives to their bank to make payments of a fixed amount at regular intervals.
     * Payments are made automatically by the bank on a defined schedule (e.g. weekly or monthly) on an ongoing basis, unless a specified condition has been met, such as an end-date being reached or a set number of payments having been made.
     * Standing Orders can only be created, amended or cancelled by the account holder, typically by using their online or telephone banking service. They are most commonly used for recurring payments where the amount stays the same, such as rent payments,
     * subscription services or regular account top-ups.
     */
    val frPayment by lazy {
        handleDI()
        EcoDi.inject<FrPayment>()
    }

    /**
     * Paylink API
     * Note: The Ecospend Gateway presents Paylink as an alternative and easier form of Open Banking Instant Payment solution.
     * Paylink provides you the option of downsizing the development effort for a PIS journey to a single endpoint integration.
     * Paylink undertakes all of interaction in the payment user journey with your branding on display.
     */
    val payment by lazy {
        handleDI()
        EcoDi.inject<Payment>()
    }

    /**
     * VRPlink (Variable Recurring Payment ) API
     * Variable Recurring Payments (VRPs) let customers safely connect authorised payments providers to their bank account so that they can make payments on the customer’s behalf, in line with agreed limits.
     * VRPs offer more control and transparency than existing alternatives, such as Direct Debit payments.
     */
    val vrplink by lazy {
        handleDI()
        EcoDi.inject<VRPlink>()
    }

    /**
     *  Bulk Payment API
     * A bulk payment is a payment created from a bulk list - so it's a payment to multiple beneficiaries from a single debit account.
     * It will show as one debit on your bank statement. As with bulk lists, there are two types: standard domestic bulk payments and bulk Inter Account Transfers (IATs).
     */
    val bulkPayment by lazy {
        handleDI()
        EcoDi.inject<BulkPayment>()
    }

    /**
     * Sets configuration for all PayByBank APIs.
     * Note: For more details, please look at the [API Specifications & Developer's Guide](https://docs.ecospend.com/api/intro).
     * Warning: This method should be called before using any PayByBank API, otherwise each API returns an error as `PayByBankError.notConfigured`.
     *
     *@property environment: Instance's `PayByBankEnvironment`, which is environment for testing or released applications.
     *@property authentication: Instance's `PayByBankAuthentication`, which is configuration for authentication to Ecospend Gateway APIs.
     */
    fun configure(
        environment: PayByBankEnvironment,
        authentication: PayByBankAuthentication
    ) = PayByBankState.Config
        .apply {
            this.authentication = authentication
            this.environment = environment
        }

    private fun handleDI() {
        PayByBankState.Network.tokenResponse = null
        AppDI.clear()
        AppDI.setup()
    }
}
