package com.example.noteapp02

import android.app.Application
import com.example.noteapp02.utils.PreferenceHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
    }
}