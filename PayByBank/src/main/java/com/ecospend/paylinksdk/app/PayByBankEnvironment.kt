package com.ecospend.paybybank.app

import com.ecospend.paybybank.shared.Config

enum class PayByBankEnvironment(
    val iamUrl: String,
    val paylinkUrl: String,
    val datalinkUrl: String,
    val frPaymentUrl: String,
    var paymentUrl: String,
    val vrplinkUrl: String,
    val bulkPaymentUrl: String
) {
    Production(
        iamUrl = Config.Network.Production.IAM_URL,
        paylinkUrl = Config.Network.Production.PAYLINK_URL,
        datalinkUrl = Config.Network.Production.DATA_LINK_URL,
        frPaymentUrl = Config.Network.Production.FR_PAYMENT_URL,
        paymentUrl = Config.Network.Production.PAYMENT_URL,
        vrplinkUrl = Config.Network.Production.VRP_LINK_URl,
        bulkPaymentUrl = Config.Network.Production.BULK_PAYMENT_URL

    ),
    Sandbox(
        iamUrl = Config.Network.Sandbox.IAM_URL,
        paylinkUrl = Config.Network.Sandbox.PAYLINK_URL,
        datalinkUrl = Config.Network.Sandbox.DATA_LINK_URL,
        frPaymentUrl = Config.Network.Sandbox.FR_PAYMENT_URL,
        paymentUrl = Config.Network.Sandbox.PAYMENT_URL,
        vrplinkUrl = Config.Network.Sandbox.VRP_LINK_URl,
        bulkPaymentUrl = Config.Network.Sandbox.BULK_PAYMENT_URL
    )
}

fun PayByBankEnvironment.urls() = listOf(
    iamUrl, paylinkUrl, datalinkUrl, frPaymentUrl, paymentUrl, vrplinkUrl, bulkPaymentUrl
)
