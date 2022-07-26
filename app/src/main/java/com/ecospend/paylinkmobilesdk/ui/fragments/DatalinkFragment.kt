package com.ecospend.paylinkmobilesdk.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ecospend.paylinkmobilesdk.R
import com.ecospend.paylinkmobilesdk.databinding.FragmentDatalinkBinding
import com.ecospend.paylinksdk.app.PayByBank
import com.ecospend.paylinksdk.data.remote.model.PayByBankNotificationOptionsRequest
import com.ecospend.paylinksdk.data.remote.model.datalink.*
import com.ecospend.paylinksdk.data.remote.model.datalink.request.DatalinkCreateRequest
import com.ecospend.paylinksdk.data.remote.model.datalink.response.ConsentPermission
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paylinksdk.shared.model.completion.PayByBankStatus

class DatalinkFragment : Fragment() {

    private lateinit var binding: FragmentDatalinkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_datalink, container, false)
        handleDatalinkRequest()
        return binding.root
    }

    private fun handleDatalinkRequest() {

        binding.button.setOnClickListener {
            val request = getDatalinkCreateRequest()

            PayByBank.datalink.initiate(
                activity as AppCompatActivity,
                request = request
            ) { result, error ->

                when {
                    result?.status == PayByBankStatus.Initiated -> {
                        Toast.makeText(activity, "Initiated", Toast.LENGTH_LONG).show()
                    }
                    result?.status == PayByBankStatus.Cancelled -> {
                        Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
                    }
                    error != null -> {
                        Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun getDatalinkCreateRequest(): DatalinkCreateRequest {
        return DatalinkCreateRequest(
            redirectURL = binding.redirectURl.text.toString(),
            bankId = binding.bankId.text.toString(),
            merchantId = binding.merchantId.text.toString(),
            merchantUserId = binding.merchantUserId.text.toString(),
            consentEndDate = binding.consentEndDate.text.toString(),
            expiryDate = binding.expiryDate.text.toString(),
            permissions = binding.permissions.text.toString()
                .split(",")
                .toList()
                .map { ConsentPermission.valueOf(it) },
            datalinkOptions = DatalinkOptions(
                autoRedirect = binding.autoRedirect.isChecked,
                generateQrCode = binding.generateQrCode.isChecked,
                allowMultipleConsent = binding.allowMultipleConsent.isChecked,
                generateFinancialReport = binding.generateQrCode.isChecked
            ),
            financialReport = FinancialReport(
                filters = Filters(
                    startDate = "2020-08-24T14:15:22Z",
                    currency = PayByBankCurrency.GBP
                ),
                parameters = FinancialReportParameters(
                    affordability = null,
                    verification = VerificationParameters(
                        name = null,
                        phoneNumbers = null,
                        address = null,
                        email = null
                    ),
                    financial = FinancialMultiParameters(
                        financial = true
                    ),
                    categoryAggregation = CategoryAggregationParameters(
                        distributionPeriod = DistrubutionPeriod.Year
                    )
                ),
                outputSettings = OutputSettings(displayPii = true)
            ),
            notificationOptions = PayByBankNotificationOptionsRequest(
                sendEmailNotification = false,
                email = "example@example.com",
                sendSMSNotification = false,
                phoneNumber = ""
            )
        )
    }
}