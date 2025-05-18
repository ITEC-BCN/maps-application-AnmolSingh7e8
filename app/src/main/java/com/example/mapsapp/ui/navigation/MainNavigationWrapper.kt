package com.example.mapsapp.ui.navigation


import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.screens.DrawerScreen
import com.example.mapsapp.ui.screens.LoginScreen
import com.example.mapsapp.ui.screens.PermissionsScreen
import com.example.mapsapp.ui.screens.RegisterScreen
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModel
import com.example.mapsapp.viewmodels.AuthViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(UnstableApi::class)
@Composable
fun MainNavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController, Destination.Permissions) {
        //Composable Permissions screen
        composable<Destination.Permissions> {
            PermissionsScreen {
                navController.navigate(Destination.Login) {
                    popUpTo<Destination.Login> { inclusive = true }
                }

            }
        }
        //Composable Login screen
        composable<Destination.Login> {
            LoginScreen(
                NavigateToHome = {
                    navController.navigate(Destination.Drawer) {
                        popUpTo<Destination.Login> { inclusive = true }
                    }
                },
                NavigateToRegister = {
                    navController.navigate(Destination.SignUp) {
                        popUpTo<Destination.Login> { inclusive = true }
                    }
                }
            )
        }
        //Composable Register screen
        composable<Destination.SignUp> {
            RegisterScreen(
                NavigateToHome = {
                    navController.navigate(Destination.Drawer) {
                        popUpTo<Destination.Login> { inclusive = true }
                    }
                },
                NavigateToLogin = {
                    navController.navigate(Destination.Login) {
                        popUpTo<Destination.Login> { inclusive = true }
                    }
                }
            )
        }
        //Composable Drawer screen
        composable<Destination.Drawer> {
            DrawerScreen(
                LogOut = {
                    navController.navigate(Destination.Login) {
                        popUpTo<Destination.Login> { inclusive = true }
                    }
                }
            )
        }
    }
}