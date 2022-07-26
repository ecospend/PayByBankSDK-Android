package com.ecospend.paylinksdk.shared.model.redirect

import com.google.gson.annotations.SerializedName

sealed class RedirectType {

    class UserCancelled(
        val error: String?,

        @SerializedName("paylink_id")
        val paylinkID: String?
    ) : RedirectType()
}
