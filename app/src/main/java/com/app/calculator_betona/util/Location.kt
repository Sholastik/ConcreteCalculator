package com.app.calculator_betona.util

import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.location.Location
import com.app.calculator_betona.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import java.util.*

fun parseLocation(context: Context, location: Location): String {
    Geocoder(context, Locale.US)
        .getFromLocation(location.latitude, location.longitude, 10).forEach {
            if (!it.locality.isNullOrEmpty()) {
                return it.locality
            }
        }
    return context.getString(R.string.default_city)
}

fun checkPlayServices(context: Context) = GoogleApiAvailability.getInstance()
    .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS

fun setCity(context: Context, pref: SharedPreferences, city: String) {
    pref.edit().run {
        putString(
            context.getString(R.string.preference_city_name),
            city
        )
        apply()
    }
}

fun getCityName(context: Context, pref: SharedPreferences) =
    pref.getString(
        context.getString(R.string.preference_city_name),
        context.getString(R.string.default_city)
    )!!
