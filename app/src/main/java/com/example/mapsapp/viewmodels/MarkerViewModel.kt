package com.example.mapsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MarkerViewModel : ViewModel() {
    var selectedMarker: LatLng? = null
}