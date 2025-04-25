package com.example.mapsapp.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.utils.PermissionStatus
import com.example.mapsapp.viewmodels.PermissionViewModel

@Composable
fun PermissionsScreen(navigateToDrawer: () -> Unit) {
    val activity = LocalContext.current as? Activity
    val viewModel = viewModel<PermissionViewModel>()

    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    val permissionsStatus = viewModel.permissionsStatus.value
    var alreadyRequested by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result: Map<String, Boolean> ->
        permissions.forEach { permission ->
            val granted = result[permission] ?: false
            val status = when {
                granted -> PermissionStatus.Granted
                ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission) -> PermissionStatus.Denied
                else -> PermissionStatus.PermanentlyDenied
            }
            viewModel.updatePermissionStatus(permission, status)
        }
    }

    LaunchedEffect(Unit) {
        if (!alreadyRequested) {
            alreadyRequested = true
            launcher.launch(permissions.toTypedArray())
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Permissions status:", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        permissions.forEach { permission ->
            val status = permissionsStatus[permission]
            val label = when (status) {
                null -> "Requesting..."
                PermissionStatus.Granted -> "Granted"
                PermissionStatus.Denied -> "Denied"
                PermissionStatus.PermanentlyDenied -> "Permanently denied"
            }
            val permissionName = permission.removePrefix("android.permission.")
            Text("$permissionName: $label")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (permissions.any {
                permissionsStatus[it] == PermissionStatus.Denied
            }
        ) {
            Button(onClick = {
                launcher.launch(permissions.toTypedArray())
            }) {
                Text("Apply again")
            }
        } else {
            navigateToDrawer()
        }

        if (permissions.any {
                permissionsStatus[it] == PermissionStatus.PermanentlyDenied
            }
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", activity!!.packageName, null)
                }
                activity!!.startActivity(intent)
            }) {
                Text("Go to settings")
            }
        } else{
            navigateToDrawer()
        }
    }
}