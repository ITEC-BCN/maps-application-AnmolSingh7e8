package com.example.mapsapp.ui.navigation


import kotlinx.serialization.Serializable

sealed class Destination {

    @Serializable
    object Login: Destination()

    @Serializable
    object SignUp: Destination()

    @Serializable
    object Permissions: Destination()

    @Serializable
    object Drawer: Destination()

    @Serializable
    object Map: Destination()

    @Serializable
    object List: Destination()

    @Serializable
    data class MarkerCreation(val coordeandes: String): Destination()

    @Serializable
    data class MarkerDetail(val id:Int): Destination()

}