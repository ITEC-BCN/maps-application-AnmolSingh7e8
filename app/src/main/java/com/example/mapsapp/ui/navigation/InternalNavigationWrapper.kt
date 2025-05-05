package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mapsapp.ui.screens.DetailMakerScreen
import com.example.mapsapp.ui.screens.MakerListScreen
import com.example.mapsapp.ui.screens.MapsScreen

@Composable
fun InternalNavigationWrapper(
    navController: NavHostController,
    padding: Modifier,
    function: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = Destination.Map,
        modifier = padding
    ) {
        composable<Destination.Map> {
            MapsScreen{latLng -> navController.navigate(Destination.MarkerCreation(coordeandes = latLng))}
        }
        composable<Destination.List> { backStackEntry ->
            val listScreen = backStackEntry.toRoute<Destination.List>()
            MakerListScreen {
                navController.navigate(Destination.Map) {
                    popUpTo<Destination.Map> { inclusive = true }
                }
            }
        }

        composable<Destination.MarkerCreation> { backStackEntry ->
            val markerCreation = backStackEntry.toRoute<Destination.MarkerCreation>()
            DetailMakerScreen {
                navController.navigate(Destination.Map) {
                    popUpTo<Destination.Map> { inclusive = true }
                }
            }
        }
    }
}