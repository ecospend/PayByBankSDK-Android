package com.ecospend.paylinkmobilesdk.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankAccountRequest
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankAccountType
import com.ecospend.paybybank.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paybybank.data.remote.model.payment.request.PaymentCreateRequest
import com.ecospend.paylinkmobilesdk.R
import com.ecospend.paylinkmobilesdk.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
        handlePayRequest()
        return binding.root
    }

    private fun handlePayRequest() {
        binding.buttonPay.setOnClickListener {

            val request = PaymentCreateRequest(
                redirectURL = binding.redirectURl.text.toString(),
                amount = binding.amount.text.toString().toFloat(),
                reference = binding.reference.text.toString(),
                bankID = binding.bankId.text.toString(),
                description = binding.description.text.toString(),
                creditorAccount = PayByBankAccountRequest(
                    type = PayByBankAccountType.values()
                        .firstOrNull {
                            it.name == binding.type.text.toString()
                        } ?: PayByBankAccountType.SortCode,
                    identification = binding.identification.text.toString(),
                    ownerName = binding.name.text.toString(),
                    currency = PayByBankCurrency.values()
                        .firstOrNull {
                            it.name == binding.currency.text.toString()
                        } ?: PayByBankCurrency.GBP
                )
            )
        }
    }
}
