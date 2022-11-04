package no.hiof.gruppe4.wateryourplants.ui.home

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.data.PlantRoom
import no.hiof.gruppe4.wateryourplants.home.*
import no.hiof.gruppe4.wateryourplants.ui.components.PlantRoomCard


@Composable
fun HomeScreen(
    onNavigateToRoom: (String, Int) -> Unit,
    onNavigateToCreatePlantRoom: (String) -> Unit,
    userName: String?
) {
    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository))

    val plantRoomList by viewModel.plantRoomList.observeAsState(listOf())


    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToCreatePlantRoom(userName.toString()) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding -> // TODO: why tf do we need to do this shit?
        Box(modifier = Modifier.padding(padding)) {
            RoomCards(
                onNavigateToRoom = onNavigateToRoom,
                userName = userName,
                plantRoomList = plantRoomList)
        }
    }
}

@Composable
fun ScaffoldTopAppBar(userName: String?) {
    TopAppBar(title = { userName?.let { Text(text = it) } }) // TODO: Placeholder. Change to actual username
}

@Composable
fun RoomCards(
    userName: String?,
    plantRoomList: List<PlantRoom>,
    onNavigateToRoom: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val weatherPlaceholder: Painter = painterResource(id = R.drawable.placeholder_weather)
    val descriptionPlaceholder = stringResource(id = R.string.placeholder_weather_description)
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
        Image(painter = weatherPlaceholder,
            contentDescription = descriptionPlaceholder,
            modifier
                .weight(1f, fill = false)
                .aspectRatio(weatherPlaceholder.intrinsicSize.width / weatherPlaceholder.intrinsicSize.height)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit)
        GetGPS()
        Text(text = stringResource(id = R.string.header_room), modifier.fillMaxWidth(),
            fontSize = 30.sp)
        LazyColumn {
            items(plantRoomList) {
                PlantRoomCard(
                    userName = userName,
                    onNavigationToRoom = onNavigateToRoom,
                    buttonName = it.roomName,
                    plantRoomId = it.plantRoomId )
            }
        }
    }
}

@Composable
fun GetGPS(){
    val context: Context = LocalContext.current.applicationContext
    //https://betterprogramming.pub/jetpack-compose-request-permissions-in-two-ways-fd81c4a702c
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val launcherMultiplePermissions = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
        permissionsMap ->
        val areGranted = permissionsMap.values.reduce{ acc, next -> acc && next}
        if (areGranted) {


            println("permission")
        } else {
            println("Make permission request")
        }
    }
    checkAndRequestLocationPermissions(context,permissions,launcherMultiplePermissions)

}


fun checkAndRequestLocationPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        lateinit var fusedLocationClient: FusedLocationProviderClient

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val tokenSource  = CancellationTokenSource()

        val token : CancellationToken = tokenSource.token

        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, token)
            .addOnSuccessListener { location : Location? ->
                var latitude: Double? = location?.latitude
                var longitude: Double? = location?.longitude
                println("lat: " +
                        latitude + ", long: " + longitude)
            }
    } else {
        // Request permissions
        launcher.launch(permissions)
    }
}