package com.example.mapsapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.screens.PermissionsScreen

@Composable
fun MainNavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController, Destination.Permissions) {
        composable<Destination.Permissions> {
            PermissionsScreen {
                navController.navigate(Destination.Drawer)
            }
        }
        composable<Destination.Drawer> {
            InternalNavigationWrapper(
                navController = navController,
                padding = Modifier.padding(16.dp),
                function = {}
            )
        }
    }
}