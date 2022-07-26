package com.ecospend.paylinkmobilesdk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.ecospend.paylinkmobilesdk.R
import com.ecospend.paylinkmobilesdk.ui.adapter.ViewPagerAdapter
import com.ecospend.paylinkmobilesdk.ui.fragments.*
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tablayout)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
            .apply {
                addFragment(PaylinkFragment(), "Paylink")
                addFragment(DatalinkFragment(), "Datalink")
                addFragment(FrPaymentFragment(), "FrPayment")
                addFragment(PaymentFragment(), "Payment")
                addFragment(VRPlinkFragment(), "VRPlink")
                addFragment(BulkPaymentFragment(), "BulkPayment")
            }

        tabLayout.setupWithViewPager(viewPager)
    }
}
