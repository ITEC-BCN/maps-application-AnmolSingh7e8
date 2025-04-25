package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.screens.DrawerScreen
import com.example.mapsapp.ui.screens.MakerListScreen
import com.example.mapsapp.ui.screens.MapsScreen

@Composable
fun InternalNavigationWrapper(
    navController: NavHostController,
    padding: Modifier,
    function: () -> Unit
) {

    DrawerScreen()
    val navController = rememberNavController() // Inicializa el NavController correctamente
    NavHost(
        navController = navController,
        startDestination = Destination.Map,
        modifier = padding
    ) {
        composable<Destination.Map> {
            MapsScreen()
        }
        composable<Destination.List> {
            MakerListScreen()
        }
    }
}