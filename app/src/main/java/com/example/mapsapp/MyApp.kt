package com.example.mapsapp

import android.app.Application
import com.example.mapsapp.data.MySupabaseClient

class MyApp: Application() {

    companion object {
        lateinit var database: MySupabaseClient
    }

    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient()
    }
}