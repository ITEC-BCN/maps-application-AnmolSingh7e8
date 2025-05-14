package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModel
import com.example.mapsapp.viewmodels.AuthViewModelFactory

@Composable
fun LoginScreen(NavigateToHome: () -> Unit, NavigateToRegister: () -> Unit) {
    val context = LocalContext.current
    val viewModel: AuthViewModel =
        viewModel(factory = AuthViewModelFactory(SharedPreferencesHelper(context)))
    val authState = viewModel.authState.value
    val showError = viewModel.showError.value

    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    if (authState == AuthState.Authenticated) {
        NavigateToHome()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = { viewModel.editEmail(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { viewModel.editPassword(it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.signIn()
            }) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { NavigateToRegister() }) {
                Text("Don't have an account? Register")
            }

            if (showError == true) {
                val errorMessage = (authState as? AuthState.Error)?.message
                Toast.makeText(
                    context,
                    errorMessage ?: "An error has occurred",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.errorMessageShowed()
            }
        }
    }
}