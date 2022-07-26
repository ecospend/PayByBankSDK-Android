package com.ecospend.paylinksdk.data.remote.model.paylink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PayByBankNotificationOptionsResponse(

    /**
     * True if SendEmailNotification is true and email was sent successfully, otherwise false
     */
    @SerializedName("is_email_sent")
    val isEmailSent: Boolean? = null,

    /**
     * True if SendSmsNotification is true and sms was sent successfully, otherwise false
     */
    @SerializedName("is_sms_sent")
    val isSmsSent: Boolean? = null
) : Serializable
