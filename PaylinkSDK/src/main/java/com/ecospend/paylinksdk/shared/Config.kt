package com.ecospend.paylinksdk.shared

class Config {
    object Network {
        object Production {
            const val IAM_URL = "https://iam.ecospend.com/"
            const val PAYLINK_URL = "https://pis-api.ecospend.com/api/v2.1/"
            const val DATA_LINK_URL = "https://ais-api.ecospend.com/api/v2.0/"
            const val FR_PAYMENT_URL = "https://pis-api.ecospend.com/api/v2.1/"
            const val PAYMENT_URL = "https://pis-api.ecospend.com/api/v2.0/"
            const val VRP_LINK_URl = "https://pis-api.ecospend.com/api/v2.1/"
            const val BULK_PAYMENT_URL = "https://pis-api.ecospend.com/api/v2.1/"
        }

        object Sandbox {
            const val IAM_URL = "https://iamapi-px01.ecospend.com/"
            const val PAYLINK_URL = "https://pis-api-sandbox.ecospend.com/api/v2.1/"
            const val DATA_LINK_URL = "https://aisapi.sb.ecospend.com/api/v2.0/"
            const val FR_PAYMENT_URL = "https://pis-api-sandbox.ecospend.com/api/v2.1/"
            const val PAYMENT_URL = "https://pis-api-sandbox.ecospend.com/api/v2.0/"
            const val VRP_LINK_URl = "https://pis-api-sandbox.ecospend.com/api/v2.1/"
            const val BULK_PAYMENT_URL = "https://pis-api-sandbox.ecospend.com/api/v2.1/"
        }

        var apiBaseUrl = Sandbox.IAM_URL
        const val API_TIME_OUT: Long = 5 * 60
    }

    object Log {
        const val DIRECTORY_NAME = "/Logs"
    }
}
