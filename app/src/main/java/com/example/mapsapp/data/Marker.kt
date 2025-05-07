package com.example.mapsapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Marker(
    val id: Int = 0,
    val title: String,
    val description: String,
    val latlng: String,
    val imageUrl: String
)