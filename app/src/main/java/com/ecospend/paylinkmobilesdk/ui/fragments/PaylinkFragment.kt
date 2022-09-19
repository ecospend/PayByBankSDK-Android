package com.ecospend.paylinkmobilesdk.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ecospend.paylinkmobilesdk.R
import com.ecospend.paylinkmobilesdk.databinding.FragmentPaylinkBinding

class PaylinkFragment : Fragment() {

    private lateinit var binding: FragmentPaylinkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_paylink, container, false)
        handlePayRequest()
        return binding.root
    }

    private fun handlePayRequest() {
        binding.buttonPay.setOnClickListener {
        }
    }
}
