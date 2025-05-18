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
import com.example.mapsapp.ui.screens.DetailMarkerScreen
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
        // Composable Mapa
        composable<Destination.Map> {
            MapsScreen { latLng -> navController.navigate(Destination.MarkerCreation(coordeandes = latLng)) }
            // Composable List
            composable<Destination.List> {
                MakerListScreen() { id ->
                    navController.navigate(Destination.MarkerDetail(id)) {
                        popUpTo<Destination.Map> { inclusive = true }
                    }
                }
            }

            // Composable Detalle
            composable<Destination.MarkerDetail> {
                val markerDetail = it.toRoute<Destination.MarkerDetail>()
                DetailMarkerScreen(id = markerDetail.id) {
                    navController.navigate(Destination.List) {
                        popUpTo(Destination.Map) { inclusive = true }
                    }
                }
            }

            // Composable Crear
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
}