package com.app.calculator_betona

import android.app.Application
import hotchemi.android.rate.AppRate

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AppRate.with(this).apply {
            setInstallDays(0)
            setLaunchTimes(3)
            setRemindInterval(7)
            setShowNeverButton(false)
        }.monitor()
    }
}