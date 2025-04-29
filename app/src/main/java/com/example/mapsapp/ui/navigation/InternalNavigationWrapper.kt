package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mapsapp.ui.screens.DetailMakerScreen
import com.example.mapsapp.ui.screens.MakerListScreen
import com.example.mapsapp.ui.screens.MapsScreen
import com.google.android.gms.maps.model.LatLng

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
            MapsScreen{latLng -> navController.navigate(Destination.MarkerCreation(latLng))}
        }
        composable<Destination.List> {
            MakerListScreen()
        }
        composable<Destination.MarkerCreation> {
            DetailMakerScreen()
        }
    }
}