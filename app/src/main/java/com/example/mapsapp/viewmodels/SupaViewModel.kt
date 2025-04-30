package com.example.mapsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SupaViewModel: ViewModel() {
    val database = MyApp.database

    private val _markerList = MutableLiveData<List<Marker>>()
    val studentsList = _markerList

    val _selectedMarker: Marker? = null

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
            val databaseStudents = database.getMarkers()
            withContext(Dispatchers.Main) {
                _markerList.value = databaseStudents
            }
        }
    }

    fun insertMarker(id: Int, name:String, coordenades: String, description: String, imgUrl: String) {
        val newMarker = Marker(
            id = id,
            title = name,
            description = description,
            latlng = coordenades,
            imageUrl = imgUrl
        )
        CoroutineScope(Dispatchers.IO).launch {
            database.insertMarker(newMarker)
            getAllMarkers()
        }
    }
    fun updateStudent(id: Int, name:String, coordenades: String, description: String, imgUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.updateMarker(
                id = id,
                title = name,
                coordenades = coordenades,
                description = description,
                imgUrl = imgUrl
            )
        }
    }

    fun deleteStudent(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteMarker(id)
            getAllMarkers()
        }
    }

    fun editMarkTitle(name: String) {
        _markerName.value = name
    }

    fun editMarkCoordenades(coordenades: String) {
        _coordenades.value = coordenades
    }

    fun editMarkDescription(description: String) {
        _description.value =  description
    }

    fun editMarkImageUrl(imaUrl: String) {
        _imgUrl.value = imaUrl
    }
}