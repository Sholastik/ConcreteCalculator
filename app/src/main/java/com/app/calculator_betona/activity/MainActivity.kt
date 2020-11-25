package com.app.calculator_betona.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.calculator_betona.R
import com.app.calculator_betona.activity.TopicItemActivity.Companion.EXTRA_TOPIC_ITEM_INDEX
import com.app.calculator_betona.data.City
import com.app.calculator_betona.databinding.ActivityMainBinding
import com.app.calculator_betona.util.DatabaseInterface
import com.app.calculator_betona.util.getCity
import com.app.calculator_betona.util.getCityName
import com.app.calculator_betona.util.logEvent
import com.app.calculator_betona.view.TopicItem

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var city: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        binding.navView.setupWithNavController(navController)

        this.city = City.getDefaultCity()
        setTopics()
        fetchCity()
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
                override fun onDataReceived(city: City) {
                    this@MainActivity.city = city
                }
            }
        )
    }

    fun showResults() {
        logEvent(
            this,
            "Calculate_Button_Pressed",
            getCityName(
                this,
                getSharedPref()
            )
        )
        navController.navigate(R.id.navigation_order)
    }

    private fun setTopics() {
        val titles = resources.getStringArray(R.array.titles)
        val array = resources.obtainTypedArray(R.array.drawables)
        val drawables = Array(array.length()) { i -> array.getDrawable(i)!! }
        array.recycle()

        require(titles.size == drawables.size) { "Title array length doesn't match the drawable array in /values/topics.xml" }
        var item = 0
        (titles zip drawables).forEach {
            val copy = item++
            addTopic(it.first, it.second, View.OnClickListener {
                if (copy == 0) {
                    openWebView()
                } else {
                    openTopic(copy)
                }
            })
        }
    }

    private fun openWebView() {
        val intent = Intent(this, WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.EXTRA_URL, getString(R.string.web_site))
        }
        startActivity(intent)
    }

    private fun openTopic(index: Int) {
        val intent = Intent(this, TopicItemActivity::class.java).apply {
            putExtra(EXTRA_TOPIC_ITEM_INDEX, index)
        }
        startActivity(intent)
    }

    private fun addTopic(text: String, drawable: Drawable, onClickListener: View.OnClickListener) {
        binding.topicLayout.addView(TopicItem(this).apply {
            setText(text)
            setDrawable(drawable)
            setOnClickListener(onClickListener)
        })
    }

    fun getSharedPref(): SharedPreferences =
        application.getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)
}
