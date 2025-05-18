package com.example.mapsapp.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.mapsapp.viewmodels.SupaViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(UnstableApi::class)
@Composable
fun MapsScreen(NavigateToDetailScreen: (String) -> Unit) {
    //Variables
    val supaViewModel: SupaViewModel = viewModel()
    val markers = supaViewModel.markerList.observeAsState()

    //Llamada a la función para obtener los marcadores
    LaunchedEffect(Unit) {
        supaViewModel.getAllMarkers()
    }

    // Composable para el mapa
    Column(modifier = Modifier.fillMaxSize()) {
        val initialPosition = LatLng(41.4534225, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(initialPosition, 17f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLongClick = { latLng ->
                val coordenadas = "${latLng.latitude},${latLng.longitude}"
                NavigateToDetailScreen(coordenadas)
            }
        ) {
            markers.value?.forEach { marker ->

                    // Limpia el formato "lat/lng: (lat, lng)" para extraer solo los números
                    val cleanedLatLng = marker.latlng
                        .replace("lat/lng: (", "") // Elimina "lat/lng: ("
                        .replace(")", "")         // Elimina ")"
                    val latLngParts = cleanedLatLng.split(",")
                    if (latLngParts.size == 2) {
                        val lat = latLngParts[0].trim().toDouble()
                        val lon = latLngParts[1].trim().toDouble()
                        val position = LatLng(lat, lon)
                        Marker(
                            state = MarkerState(position = position),
                            title = marker.title,
                            snippet = marker.description
                        )
                    } else {
                        Log.e("MapsScreen", "Formato de LatLng incorrecto: ${marker.latlng}")
                    }
                    Log.e("MapsScreen", "Error al procesar LatLng: ${marker.latlng}")
            }
        }
    }
}