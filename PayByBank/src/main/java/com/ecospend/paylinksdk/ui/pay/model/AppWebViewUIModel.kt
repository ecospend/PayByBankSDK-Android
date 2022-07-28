package com.ecospend.paybybank.ui.pay.model

import com.ecospend.paybybank.shared.model.completion.PayByBankError
import com.ecospend.paybybank.shared.model.completion.PayByBankResult

class AppWebViewUIModel(
    val uniqueID: String,
    val webViewURL: String,
    val redirectURL: String,
    val completion: (PayByBankResult?, PayByBankError?) -> Unit,
)
