package com.ecospend.paybybank.shared

class Config {
    object Network {
        object Production {
            const val IAM_URL = "https://ecospend.com/"
            const val PAYLINK_URL = "https://ecospend.com/"
            const val DATA_LINK_URL = "https://ecospend.com/"
            const val FR_PAYMENT_URL = "https://ecospend.com/"
            const val PAYMENT_URL = "https://ecospend.com/"
            const val VRP_LINK_URl = "https://ecospend.com/"
            const val BULK_PAYMENT_URL = "https://ecospend.com/"
        }

        object Sandbox {
            const val IAM_URL = "https://ecospend.com/"
            const val PAYLINK_URL = "https://ecospend.com/"
            const val DATA_LINK_URL = "https://ecospend.com/"
            const val FR_PAYMENT_URL = "https://ecospend.com/"
            const val PAYMENT_URL = "https://ecospend.com/"
            const val VRP_LINK_URl = "https://ecospend.com/"
            const val BULK_PAYMENT_URL = "https://ecospend.com/"
        }

        var apiBaseUrl = Sandbox.IAM_URL
        const val API_TIME_OUT: Long = 5 * 60
    }

    object Log {
        const val DIRECTORY_NAME = "/Logs"
    }
}
