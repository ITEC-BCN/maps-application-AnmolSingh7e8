package com.example.mapsapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val route: Destination
) {
    MAP(Icons.Default.Place, "Map", Destination.Map),
    LIST(Icons.Default.LocationOn, "List", Destination.List),
}
