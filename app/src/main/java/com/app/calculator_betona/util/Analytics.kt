package com.app.calculator_betona.util

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

fun logEvent(context: Context, event: String, city: String) =
    FirebaseAnalytics.getInstance(context).run {
        setUserProperty("city", city)
        logEvent(
            event,
            Bundle().apply {
                putString("city", city)
            }
        )
    }