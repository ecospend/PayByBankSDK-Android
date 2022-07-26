package com.ecospend.paylinksdk.ui.pay.model

import com.ecospend.paylinksdk.shared.model.completion.PayByBankError
import com.ecospend.paylinksdk.shared.model.completion.PayByBankResult

class AppWebViewUIModel(
    val uniqueID: String,
    val webViewURL: String,
    val redirectURL: String,
    val completion: (PayByBankResult?, PayByBankError?) -> Unit,
)
