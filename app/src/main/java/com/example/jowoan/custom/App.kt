package com.example.jowoan.custom

import android.app.Application
import android.content.res.Resources
import com.jakewharton.threetenabp.AndroidThreeTen

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
        AndroidThreeTen.init(this)
    }

    companion object {
        var instance: App? = null
            private set
        var resourses: Resources? = null
            private set

    }
}