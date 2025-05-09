package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mapsapp.ui.screens.CreateMarkerScreen
import com.example.mapsapp.ui.screens.MakerListScreen
import com.example.mapsapp.ui.screens.MapsScreen

@RequiresApi(Build.VERSION_CODES.O)
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
            MapsScreen { latLng -> navController.navigate(Destination.MarkerCreation(coordeandes = latLng)) }
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
            CreateMarkerScreen(cordenadas = markerCreation.coordeandes) { // Pass the coordinates
                navController.navigate(Destination.Map) {
                    popUpTo<Destination.Map> { inclusive = true }
                }
            }
        }
    }
}