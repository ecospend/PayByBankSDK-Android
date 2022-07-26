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
import com.ecospend.paylinkmobilesdk.databinding.FragmentPaylinkBinding
import com.ecospend.paylinkmobilesdk.databinding.FragmentVrplinkBinding
import com.ecospend.paylinksdk.app.PayByBank
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountRequest
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankAccountType
import com.ecospend.paylinksdk.data.remote.model.paylink.PayByBankCurrency
import com.ecospend.paylinksdk.data.remote.model.paylink.request.PaylinkCreateRequest
import com.ecospend.paylinksdk.data.remote.model.vrplink.request.VRPReason
import com.ecospend.paylinksdk.data.remote.model.vrplink.request.VRPlinkCreateRequest
import com.ecospend.paylinksdk.shared.model.completion.PayByBankStatus

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
            PayByBank.vrplink.initiate(activity as AppCompatActivity, request) { result, error ->

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