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
import com.ecospend.paylinkmobilesdk.databinding.FragmentBulkpaymentBinding
import com.ecospend.paylinkmobilesdk.databinding.FragmentPaymentBinding
import com.ecospend.paylinksdk.app.PayByBank
import com.ecospend.paylinksdk.data.remote.model.bulkPayment.BulkPaymentCreateRequest
import com.ecospend.paylinksdk.data.remote.model.bulkPayment.BulkPaymentPaylinkEntry
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountType
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paylinksdk.data.remote.model.payment.request.PaymentCreateRequest
import com.ecospend.paylinksdk.shared.model.completion.PayByBankStatus

class BulkPaymentFragment : Fragment() {

    private lateinit var binding: FragmentBulkpaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bulkpayment, container, false)
        handlePayRequest()
        return binding.root
    }


    private fun handlePayRequest() {
        binding.buttonPay.setOnClickListener {

            val request = BulkPaymentCreateRequest(
                redirectURL = binding.redirectURl.text.toString(),
                reference = binding.reference.text.toString(),
                fileReference = binding.fileReference.text.toString(),
                description = binding.description.text.toString(),
                payments = listOf(
                    BulkPaymentPaylinkEntry(
                        creditorAccount = PayByBankAccountRequest(
                            type = PayByBankAccountType.values()
                                .firstOrNull {
                                    it.name == binding.type.text.toString()
                                } ?: PayByBankAccountType.SortCode,
                            identification = binding.identification.text.toString(),
                            name = binding.name.text.toString(),
                            currency = PayByBankCurrency.values()
                                .firstOrNull {
                                    it.name == binding.currency.text.toString()
                                } ?: PayByBankCurrency.GBP
                        ),
                        amount = binding.amount.text.toString().toFloatOrNull(),
                        reference = binding.reference.text.toString()
                    )
                )
            )
            PayByBank.bulkPayment.initiate(activity as AppCompatActivity, request) { result, error ->

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
}