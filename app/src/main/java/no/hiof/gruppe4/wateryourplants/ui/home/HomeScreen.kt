package no.hiof.gruppe4.wateryourplants.ui.home

import androidx.annotation.RequiresApi
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.data.PlantRoom
import no.hiof.gruppe4.wateryourplants.home.*
import no.hiof.gruppe4.wateryourplants.ui.components.PlantRoomCard
import org.json.JSONArray
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL

// TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
@RequiresApi(value = 26)
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
                plantRoomList = plantRoomList,
                viewModel = viewModel)
        }
    }
}

@Composable
fun ScaffoldTopAppBar(userName: String?) {
    TopAppBar(title = { userName?.let { Text(text = it) } }) // TODO: Placeholder. Change to actual username
}

// TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
@RequiresApi(value = 26)
@Composable
fun RoomCards(
    userName: String?,
    plantRoomList: List<PlantRoom>,
    onNavigateToRoom: (String, Int) -> Unit,
    viewModel: PlantViewModel,
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
        GetGPS(modifier = modifier)
        Text(text = stringResource(id = R.string.header_room), modifier.fillMaxWidth(),
            fontSize = 30.sp)
        LazyColumn {
            items(plantRoomList) {
                PlantRoomCard(
                    userName = userName,
                    onNavigationToRoom = onNavigateToRoom,
                    buttonName = it.roomName,
                    plantRoomId = it.plantRoomId,
                    viewModel1 = viewModel)
            }
        }
    }
}


@Composable
fun GetGPS(modifier: Modifier) {
    var weatherMutableMap: MutableMap<String, String> = mutableMapOf()
    val context: Context = LocalContext.current.applicationContext
    //https://betterprogramming.pub/jetpack-compose-request-permissions-in-two-ways-fd81c4a702c
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS
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

    SideEffect{ weatherMutableMap = checkAndRequestLocationPermissions(context, permissions, launcherMultiplePermissions) }

    // TODO: Have to await API response
/*
    Row(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "Clouds",
            modifier = modifier
                .padding(5.dp)
                .fillMaxWidth(0.5f),
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(text = weatherMutableMap.get("clouds")!!,
            modifier = modifier
                .padding(5.dp)
                .fillMaxWidth(),
            fontSize = 18.sp
        )
    }

 */
}


fun checkAndRequestLocationPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
): MutableMap<String, String> {
    var weatherMutableMap: MutableMap<String, String> = mutableMapOf()

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

                latitude = latitude?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                longitude = longitude?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble() }
                val urlWithGps = URL("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=$latitude&lon=$longitude")
                val coroutineScope = CoroutineScope(Dispatchers.IO)
                var responseCode: Int = 0
                coroutineScope.launch {
                    val connectionToYr: HttpURLConnection = urlWithGps.openConnection() as HttpURLConnection
                    connectionToYr.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
                    connectionToYr.requestMethod = "GET"
                    connectionToYr.setRequestProperty("User-Agent", "waterYourPlants")
                    connectionToYr.doInput = true
                    connectionToYr.doOutput = false
                   responseCode = connectionToYr.responseCode

                    if(responseCode == 200) {
                        val response = connectionToYr.inputStream.bufferedReader()
                            .use { it.readText() }  // defaults to UTF-8
                        withContext(Dispatchers.Main) {

                            // Convert raw JSON to pretty JSON using GSON library
                            val gson = GsonBuilder().setPrettyPrinting().create()
                            var jsonParser = JsonParser()



                            //val prettyJson = gson.toJson(jsonParser.parse(response))

                           // println(prettyJson)
                            //Log.d("Pretty Printed JSON :", prettyJson)

                            val jsonObject = JSONObject(response)
                            val airTemp = jsonObject.getJSONObject("properties")
                                .getJSONArray("timeseries")
                                .getJSONObject(1)
                                .getJSONObject("data")
                                .getJSONObject("instant")
                                .getJSONObject("details")
                                .get("air_temperature")
                            val clouds = jsonObject.getJSONObject("properties")
                                .getJSONArray("timeseries")
                                .getJSONObject(1)
                                .getJSONObject("data")
                                .getJSONObject("next_1_hours")
                                .getJSONObject("summary")
                                .get("symbol_code")

                            weatherMutableMap.put(key = "airTemp", value = airTemp.toString())
                            weatherMutableMap.put(key = "clouds", value = clouds.toString())
                        }
                    } else {
                        println("HTTPURLCONNECTION_ERROR" +responseCode.toString())
                    }
                    println(responseCode)
                   }
                println(responseCode)

            }

        // Use location because permissions are already granted
    } else {
        // Request permissions
        launcher.launch(permissions)
    }
    return weatherMutableMap
}