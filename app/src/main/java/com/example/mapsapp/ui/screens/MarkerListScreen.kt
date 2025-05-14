package com.example.mapsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.SupaViewModel
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.ui.draw.clip


@Composable
fun MakerListScreen(NavigateToDetail: (Int) -> Unit) {
    val supaViewModel: SupaViewModel = viewModel()
    val markers by supaViewModel.markerList.observeAsState(emptyList())
    supaViewModel.getAllMarkers()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF121212), Color(0xFF1E1E1E), Color(0xFF2A2A2A))
                )
            )
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                if(markers.isEmpty()) {
                    Text(
                        text = "Abans has d'afegir un marcador per editar-lo",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFB0BEC5),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                } else{
                    Text(
                        text = "Selecciona un marcador per editar-lo",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFB0BEC5),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            items(markers, key = { it.id }) { marker ->
                val dismissState = rememberSwipeToDismissBoxState()

                if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart && dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                    LaunchedEffect(marker.id) {
                        supaViewModel.deleteImatge(marker.id, marker.imageUrl)
                    }
                }

               SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromEndToStart = true,
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(shape = RoundedCornerShape(20.dp)) // Ajusta la forma aquí
                                .background(Color.Red),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(shape = RoundedCornerShape(20.dp)) // Asegura que la forma sea consistente
                            .shadow(16.dp, shape = RoundedCornerShape(20.dp))
                            .clickable { NavigateToDetail(marker.id) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF263238))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = marker.title,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB0BEC5)
                                )
                                Text(
                                    text = marker.description,
                                    fontSize = 18.sp,
                                    color = Color(0xFFCFD8DC)
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = Color(0xFF90A4AE),
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}