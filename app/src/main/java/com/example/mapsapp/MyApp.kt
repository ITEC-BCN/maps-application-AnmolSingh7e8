package com.example.mapsapp

import android.app.Application
import com.example.mapsapp.data.MySupabaseClient

class MyApp: Application() {

    companion object {
        lateinit var database: MySupabaseClient
    }

    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient(
            supabaseUrl = "https://dfqholekpaoudugakjtr.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRmcWhvbGVrcGFvdWR1Z2FranRyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDU4Mjc3ODQsImV4cCI6MjA2MTQwMzc4NH0.l5AzTOvwK5h18DOTmGwFYb96cUYSsLjz8F6XMkUZxrE"
        )
    }
}