package com.app.calculator_betona.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.calculator_betona.R
import com.app.calculator_betona.databinding.ActivitySplashBinding
import com.app.calculator_betona.util.*
import com.google.android.gms.location.LocationServices
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.Trace

class SplashActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var binding: ActivitySplashBinding
    private lateinit var trace: Trace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        trace = FirebasePerformance.getInstance().newTrace("Fetching location")
        requestLocationPermission()
        setDefault()
        logEvent(this, "Loading_Screen_Opened", getCityName(this, getSharedPref()))
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                0
            )
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        trace.start()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val city = parseLocation(this, it)
            firebaseAnalytics.setUserProperty("city", city)
            setCity(this, getSharedPref(), city)
        }.addOnCompleteListener {
            proceed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (checkPlayServices(this)) {
                getLocation()
            } else {
                proceed()
            }
        }
    }

    private fun getSharedPref() =
        application.getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)

    private fun setDefault() {
        val pref = getSharedPref()
        if (!pref.contains(getString(R.string.preference_city_name))) {
            setCity(this, pref, getString(R.string.default_city))
        }
    }

    private fun proceed() {
        trace.stop()
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}
