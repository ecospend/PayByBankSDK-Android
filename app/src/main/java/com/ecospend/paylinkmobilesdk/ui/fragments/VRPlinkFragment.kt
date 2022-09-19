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
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPReason
import com.ecospend.paybybank.data.remote.model.vrplink.request.VRPlinkCreateRequest
import com.ecospend.paylinkmobilesdk.R
import com.ecospend.paylinkmobilesdk.databinding.FragmentVrplinkBinding

class VRPlinkFragment : Fragment() {

    private lateinit var binding: FragmentVrplinkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vrplink, container, false)
        handlePayRequest()
        return binding.root
    }

    private fun handlePayRequest() {
        binding.buttonPay.setOnClickListener {

            val request = VRPlinkCreateRequest(
                redirectURL = binding.redirectURl.text.toString(),
                reason = VRPReason.Other,
                reference = binding.reference.text.toString(),
                description = binding.description.text.toString(),
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
                )
            )
        }
    }
}
