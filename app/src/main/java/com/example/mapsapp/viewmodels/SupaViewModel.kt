package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class SupaViewModel : ViewModel() {
    val database = MyApp.database

    private val _markerList = MutableLiveData<List<Marker>>()
    val markerList = _markerList

    private val _selectedMarker = MutableLiveData<Marker?>()
    val selectedMarker = _selectedMarker

    private val _markerName = MutableLiveData<String>()
    val markerName = _markerName

    private val _coordenades = MutableLiveData<String>()
    val markerLat = _coordenades

    private val _description = MutableLiveData<String>()
    val markerLong = _description

    private val _imgUrl = MutableLiveData<String>()
    val markerImgUrl = _imgUrl

    fun getAllMarkers() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseMarkers = database.getMarkers()
            withContext(Dispatchers.Main) {
                _markerList.value = databaseMarkers
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertNewMarker(
        id: Int,
        title: String,
        description: String,
        latlng: String,
        image: Bitmap?
    ) {
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        CoroutineScope(Dispatchers.IO).launch {
            val imageName = database.uploadImage(stream.toByteArray())
            database.insertMarker(Marker(id, title, description, latlng, imageName))
        }
    }


    fun updateMarker(id: Int, title: String, description: String, image: Bitmap?) {
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        val imageName =
            _selectedMarker.value?.imageUrl?.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        CoroutineScope(Dispatchers.IO).launch {
            database.updateMarker(
                id,
                title,
                description,
                imageName.toString(),
                stream.toByteArray()
            )
        }
    }


    fun deleteImatge(id: String, image: String) {
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteImage(image)
            database.deleteMarker(id)
            getAllMarkers()
        }
    }


}