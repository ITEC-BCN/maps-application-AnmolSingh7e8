package com.example.mapsapp.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from


class MySupabaseClient() {

    lateinit var client: SupabaseClient

    constructor(supabaseUrl: String, supabaseKey: String) : this() {
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
        }
    }

    suspend fun getMarkers(): List<Marker> {
        return client.from("Markers").select().decodeList<Marker>()
    }

    suspend fun insertMarker(marker: Marker) {
        client.from("Markers").insert(marker)
    }

    suspend fun updateMarker(id: Int, title: String, coordenades: String, description: String, imgUrl: String) {
        client.from("Markers").update({
            set("name", title)
            set("coordenades", coordenades)
            set("description", description)
            set("imageUrl", imgUrl)
        }) {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun deleteMarker(id: String) {
        client.from("Markers").delete {
            filter {
                eq("id", id)
            }
        }
    }

}