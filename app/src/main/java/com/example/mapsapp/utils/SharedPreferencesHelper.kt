package com.example.mapsapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    fun saveAuthData(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString("access_token", accessToken)
                .putString("refresh_token", refreshToken)
        }
    }
    fun getAccessToken(): String? = sharedPreferences.getString("access_token", null)
    fun getRefreshToken(): String? = sharedPreferences.getString("refresh_token", null)
    fun clear() {
        sharedPreferences.edit { clear() }
    }
}
