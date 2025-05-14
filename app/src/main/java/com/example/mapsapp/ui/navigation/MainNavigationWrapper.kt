package com.example.mapsapp.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.screens.DrawerScreen
import com.example.mapsapp.ui.screens.LoginScreen
import com.example.mapsapp.ui.screens.PermissionsScreen
import com.example.mapsapp.ui.screens.RegisterScreen

@Composable
fun MainNavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController, Destination.Login) {
        composable<Destination.Login> {
            LoginScreen(
                NavigateToHome = { navController.navigate(Destination.Drawer) },
                NavigateToRegister = { navController.navigate(Destination.SignUp) }
            )
        }
        composable<Destination.SignUp> {
            RegisterScreen(
                NavigateToHome = { navController.navigate(Destination.Drawer) },
                NavigateToLogin = { navController.navigate(Destination.Login) }
            )
        }
        composable<Destination.Permissions> {
            PermissionsScreen {
                navController.navigate(Destination.Drawer)
            }
        }
        composable<Destination.Drawer> {
            DrawerScreen()
        }
    }
}