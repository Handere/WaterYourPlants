package no.hiof.gruppe4.wateryourplants.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.data.PlantRoom
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory
import no.hiof.gruppe4.wateryourplants.ui.components.PlantRoomCard
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL

// TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(value = 26)
@Composable
fun HomeScreen(
    onNavigateToRoom: (String, Int) -> Unit,
    onNavigateToCreatePlantRoom: (String) -> Unit,
    userName: String?
) {

    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository))
    val plantRoomList by viewModel.plantRoomList.observeAsState(listOf())

    //logic
    var weatherMutableMap: MutableMap<String, String> = mutableMapOf()
    var weatherString = ""
    var weatherChange by remember{ mutableStateOf(weatherString) }


    val context: Context = LocalContext.current.applicationContext
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val tokenSource = CancellationTokenSource()
    val token: CancellationToken = tokenSource.token
    val launcherMultiplePermissions = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()){}
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {

        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, token)
            .addOnCompleteListener { location: Task<Location> ->
                var latitude = location.result.latitude
                var longitude = location.result.longitude

                latitude = latitude.let {
                    BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                }
                longitude = longitude.let {
                    BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                }

                weatherString = "lat=$latitude&lon=$longitude"

                weatherChange = weatherString
            }


    } else {
        // Request permissions

        launcherMultiplePermissions.launch(permissions)}



        LaunchedEffect(weatherChange){

            delay(300)

            weatherMutableMap = getWeather(weatherString)

        }




    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToCreatePlantRoom(userName.toString()) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding -> // TODO: why tf do we need to do this shit?
        Column(modifier = Modifier.padding(padding)) {


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



suspend fun getWeather(latAndLon: String): MutableMap<String, String> {
    var weatherMutableMap: MutableMap<String, String> = mutableMapOf()
    val urlWithGps = URL("https://api.met.no/weatherapi/locationforecast/2.0/compact?$latAndLon")
    println(urlWithGps)
    var responseCode: Int = 0
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    coroutineScope.launch {
        val connectionToYr: HttpURLConnection = urlWithGps.openConnection() as HttpURLConnection
        connectionToYr.setRequestProperty(
            "Accept",
            "application/json"
        ) // The format of response we want to get from the server
        connectionToYr.requestMethod = "GET"
        connectionToYr.setRequestProperty("User-Agent", "waterYourPlants")
        connectionToYr.doInput = true
        connectionToYr.doOutput = false
        responseCode = connectionToYr.responseCode

        if (responseCode == 200) {
            val response = connectionToYr.inputStream.bufferedReader()
                .use { it.readText() }  // defaults to UTF-8


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

            weatherMutableMap["airTemp"] = airTemp.toString()
            weatherMutableMap.put(key = "clouds", value = clouds.toString())
            println("weather 0" + weatherMutableMap)

        } else {
            println("HTTPURLCONNECTION_ERROR" + responseCode.toString())
        }

    }.join()

        return weatherMutableMap
    }



