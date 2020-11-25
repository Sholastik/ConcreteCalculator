package com.app.calculator_betona.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.calculator_betona.R
import com.app.calculator_betona.databinding.ActivityOnBoardingBinding
import hotchemi.android.rate.AppRate

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_on_boarding
        )

        binding.calculateRatio.setOnClickListener {
            showCalculator()
        }

        binding.orderConcrete.setOnClickListener {
            showOrder()
        }

        AppRate.showRateDialogIfMeetsConditions(this)
    }

    private fun showCalculator() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showOrder() {
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
    }
}
