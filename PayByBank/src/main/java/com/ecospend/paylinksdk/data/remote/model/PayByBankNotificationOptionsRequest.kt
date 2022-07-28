package com.ecospend.paybybank.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PayByBankNotificationOptionsRequest(

    /**
     * Optional parameter for Gateway to send an email notification to the PSU with the Paylink URL.
     * Defaults to false.
     */
    @SerializedName("send_email_notification")
    val sendEmailNotification: Boolean?,

    /**
     * The email address that the email notification will be sent to.
     * Warning: This field is **mandatory** if `sendEmailNotification` is true.
     */
    val email: String?,

    /**
     * Optional parameter for Gateway to send an SMS notification to the PSU with the Paylink URL.
     * Defaults to false.
     */
    @SerializedName("send_sms_notification")
    val sendSMSNotification: Boolean?,

    /**
     * The phone number (including the country dial-in code) that the SMS notification will be sent to.
     * Warning: This field is **mandatory** if `sendSMSNotification` is true.
     */
    @SerializedName("phone_number")
    val phoneNumber: String?
) : Serializable
