package com.example.mapsapp.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.media3.common.Format
import com.example.mapsapp.BuildConfig
import com.example.mapsapp.utils.AuthState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserSession
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MySupabaseClient {
    var client: SupabaseClient
    var storage: Storage
    private val supabaseUrl = BuildConfig.SUPABASE_URL
    private val supabaseKey = BuildConfig.SUPABASE_KEY

    constructor() {
        client = createSupabaseClient(supabaseUrl = supabaseUrl, supabaseKey = supabaseKey) {
            install(Postgrest)
            install(Storage)
            install(Auth){
                autoLoadFromStorage = true
            }
        }
        storage = client.storage
    }

    //Creació de l'adreça de l'imatge i pujada a Supabase
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        Log.d("TAG", this.supabaseUrl)
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images")
            .upload(path = "image_${fechaHoraActual.format (formato)}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    fun buildImageUrl(imageFileName: String) =
        "${this.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"

    //Eliminar la imatge
    suspend fun deleteImage(imageName: String) {
        val imgName =
            imageName.removePrefix("${this.supabaseUrl}/storage/v1/object/public/images/")
        client.storage.from("images").delete(imgName)
    }

    //Llistar els marcadors
    suspend fun getMarkers(): List<Marker> {
        return client.from("Marker").select().decodeList<Marker>()
    }

    //Insertar un marcador
    suspend fun insertMarker(marker: Marker) {
        client.from("Marker").insert(marker)
    }

    //Actualitzar un marcador
    suspend fun updateMarker(
        id: Int,
        title: String,
        description: String,
        imageName: String,
        imageFile: ByteArray
    ) {
        val imageName = storage.from("images").update(path = imageName, data = imageFile)
        client.from("Marker").update({
            set("id", id)
            set("title", title)
            set("description", description)
            //set("latlng", latlng)
            set("imageUrl", buildImageUrl(imageFileName = imageName.path))
        }) {
            filter {
                eq("id", id)
            }
        }
    }

    //Eliminar un marcador
    suspend fun deleteMarker(id: Int) {
        client.from("Marker").delete {
            filter {
                eq("id", id)
            }
        }

    }

    //Autenticació

    //SignUp amb Google
    suspend fun signUpWithEmail(emailValue: String, passwordValue: String): AuthState {
        try {
            client.auth.signUpWith(Email) {
                email = emailValue
                password = passwordValue
            }
            return AuthState.Authenticated
        } catch (e: Exception) {
            return AuthState.Error(e.localizedMessage)
        }
    }

    //SignIn amb Google
    suspend fun signInWithEmail(emailValue: String, passwordValue: String): AuthState {
        try {
            client.auth.signInWith(Email) {
                email = emailValue
                password = passwordValue
            }
            return AuthState.Authenticated
        } catch (e: Exception) {
            return AuthState.Error(e.localizedMessage)
        }
    }

    //Ens retorna les dades de l'usuari actual
    fun retrieveCurrentSession(): UserSession?{
        val session = client.auth.currentSessionOrNull()
        return session
    }


    //Funció que actualitzarà la sessió activa
    fun refreshSession(): AuthState {
        try {
            client.auth.currentSessionOrNull()
            return AuthState.Authenticated
        } catch (e: Exception) {
            return AuthState.Error(e.localizedMessage)
        }
    }



}