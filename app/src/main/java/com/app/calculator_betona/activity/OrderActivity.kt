package com.app.calculator_betona.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.calculator_betona.R
import com.app.calculator_betona.data.City
import com.app.calculator_betona.databinding.ActivityOrderBinding
import com.app.calculator_betona.util.DatabaseInterface
import com.app.calculator_betona.util.getCity
import com.app.calculator_betona.util.getCityName
import com.app.calculator_betona.util.logEvent

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private var phone: String = "+74952950101"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)

        binding.order.setOnClickListener {
            showPhone(this)
        }

        setCity(City.getDefaultCity())
        fetchCity()

        logEvent(
            this,
            "Order_Screen_Opened",
            getCityName(
                this,
                application.getSharedPreferences(
                    getString(R.string.preferences),
                    Context.MODE_PRIVATE
                )
            )
        )

        logEvent(
            this,
            "Order_Concrete_Button_Pressed",
            getCityName(
                this,
                application.getSharedPreferences(
                    getString(R.string.preferences),
                    Context.MODE_PRIVATE
                )
            )
        )
    }

    private fun setCity(city: City) {
        binding.city = city.anotherCityName
        binding.price = city.price.toInt()

        phone = city.phone
        binding.executePendingBindings()
    }

    private fun fetchCity() {
        getCity(
            getCityName(
                this,
                application.getSharedPreferences(
                    getString(R.string.preferences),
                    Context.MODE_PRIVATE
                )
            ), object : DatabaseInterface {
                override fun onDataReceived(city: City) = setCity(city)
            }
        )
    }

    private fun showPhone(activity: AppCompatActivity) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:%s".format(phone))
        }
        logEvent(
            this,
            "Order_Button_Pressed",
            getCityName(
                this,
                application.getSharedPreferences(
                    getString(R.string.preferences),
                    Context.MODE_PRIVATE
                )
            )
        )
        activity.startActivity(intent)
    }
}
