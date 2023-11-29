package com.example.paymenthistory.presentation.activity.host.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paymenthistory.R

/**
 * Activity for hosting the payment history fragment.
 */
class HostPaymentHistory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_payment_history)
    }
}