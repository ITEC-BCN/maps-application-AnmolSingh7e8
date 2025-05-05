package com.example.mapsapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mapsapp.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.datetime.LocalDateTime
import java.time.format.DateTimeFormatter


class MySupabaseClient {
    lateinit var client: SupabaseClient
    lateinit var storage: Storage
    private val supabaseUrl = BuildConfig.SUPABASE_URL
    private val supabaseKey = BuildConfig.SUPABASE_KEY

    constructor() {
        client = createSupabaseClient(supabaseUrl = supabaseUrl, supabaseKey = supabaseKey) {
            install(Postgrest)
            install(Storage)
        }
        storage = client.storage
    }

    //Creació de l'adreça de l'imatge i pujada a Supabase
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        val fechaHoraActual = LocalDateTime
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images")
            .upload(path = "image_${fechaHoraActual.Format { formato }}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    fun buildImageUrl(imageFileName: String) =
        "${this.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"

    suspend fun deleteImage(imageName: String) {
        val imgName =
            imageName.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        client.storage.from("images").delete(imgName)
    }

    //Llistar els marcadors
    suspend fun getMarkers(): List<Marker> {
        return client.from("Markers").select().decodeList<Marker>()
    }

    //Insertar un marcador
    suspend fun insertMarker(marker: Marker) {
        client.from("Markers").insert(marker)
    }


    suspend fun updateMarker(
        id: Int,
        title: String,
        description: String,
        latlng: String,
        imageName: String,
        imageFile: ByteArray
    ) {
        val imageName = storage.from("images").update(path = imageName, data = imageFile)
        client.from("Student").update({
            set("id", id)
            set("title", title)
            set("description", description)
            set("latlng", latlng)
            set("image", buildImageUrl(imageFileName = imageName.path))
        }) {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun deleteMarker(id: String) {
        client.from("Student").delete {
            filter {
                eq("id", id)
            }
        }

    }
}