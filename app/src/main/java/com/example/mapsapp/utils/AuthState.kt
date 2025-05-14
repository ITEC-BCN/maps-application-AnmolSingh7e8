package com.example.mapsapp.utils

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}
