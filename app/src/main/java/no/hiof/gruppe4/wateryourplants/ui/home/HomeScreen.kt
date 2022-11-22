package no.hiof.gruppe4.wateryourplants.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import no.hiof.gruppe4.wateryourplants.WindowInfo
import no.hiof.gruppe4.wateryourplants.data.PlantRoom
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory
import no.hiof.gruppe4.wateryourplants.rememberWindowInfo
import no.hiof.gruppe4.wateryourplants.ui.components.PlantRoomCard
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    onNavigateToRoom: (String, Int) -> Unit,
    onNavigateToCreatePlantRoom: (String) -> Unit,
    userName: String?
) {
    val windowInfo = rememberWindowInfo()
    val weatherPlaceholder: Painter = painterResource(id = R.drawable.weather_forcast)
    val descriptionPlaceholder = stringResource(id = R.string.placeholder_weather_description)

    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository))

    val plantRoomList by viewModel.plantRoomList.observeAsState(listOf())
    val context: Context = LocalContext.current.applicationContext

    var permissionString: Int = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
    var permissionsGiven by remember { mutableStateOf(permissionString) }
    var weatherFromApi = "We are working on \ngetting your weather..."
    var weatherFromApiRememberState by remember { mutableStateOf(weatherFromApi) }
    var gpsFromUserString = ""
    var gpsLocationFromUserRememberState by remember{ mutableStateOf(gpsFromUserString) }

    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val tokenSource = CancellationTokenSource()
    val token: CancellationToken = tokenSource.token

    val launchScope = CoroutineScope(Dispatchers.IO)

    val launcherMultiplePermissions = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()){
        isGranted ->
            if (isGranted) {
                permissionString = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                permissionsGiven = permissionString
            }
    }

    if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

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

                gpsFromUserString = "lat=$latitude&lon=$longitude"

                gpsLocationFromUserRememberState = gpsFromUserString
            }

    } else {
        // Request permissions
        SideEffect {
            launchScope.launch { launcherMultiplePermissions.launch(Manifest.permission.ACCESS_COARSE_LOCATION) }
        }
    }
        LaunchedEffect(gpsLocationFromUserRememberState, permissionsGiven){
            if(permissionsGiven == 0 && gpsLocationFromUserRememberState != "") {
                weatherFromApi = getWeather(gpsLocationFromUserRememberState)
            }

            weatherFromApiRememberState = weatherFromApi
        }

    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToCreatePlantRoom(userName.toString()) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add))
            }
        }
    ) { padding ->

            Row(modifier = Modifier.fillMaxWidth()){
                if (windowInfo.screenWithInfo is WindowInfo.WindowType.Medium || windowInfo.screenWithInfo is WindowInfo.WindowType.Expanded) {
                    Box(
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .aspectRatio(weatherPlaceholder.intrinsicSize.width / weatherPlaceholder.intrinsicSize.height)
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(16.dp, 50.dp, 16.dp)
                    ) {

                        Image(
                            painter = weatherPlaceholder,
                            contentDescription = descriptionPlaceholder,
                            contentScale = ContentScale.FillBounds,
                            )

                        Text(
                            text = weatherFromApiRememberState,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(20.dp, 30.dp),
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                    }
                }

            Column(modifier = Modifier.weight(1f, fill = false)) {
                    if (windowInfo.screenWithInfo is WindowInfo.WindowType.Compact) {

                            Box(
                                modifier = Modifier
                                    .weight(0.5f, fill = false)
                                    .aspectRatio(weatherPlaceholder.intrinsicSize.width / weatherPlaceholder.intrinsicSize.height)
                                    .fillMaxWidth()
                                    .padding(16.dp, 16.dp, 13.dp)
                            ) {

                                Image(
                                    painter = weatherPlaceholder,
                                    contentDescription = descriptionPlaceholder,
                                    contentScale = ContentScale.Fit,

                                    )

                                Text(
                                    text = weatherFromApiRememberState,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(20.dp, 30.dp),
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                )
                            }
                    }
                RoomCards(
                    onNavigateToRoom = onNavigateToRoom,
                    userName = userName,
                    plantRoomList = plantRoomList,
                    viewModel = viewModel)
            } }
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
    viewModel: PlantViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)) {

        Text(text = stringResource(id = R.string.header_room), modifier.fillMaxWidth(),
            fontSize = 30.sp)
        LazyColumn {
            items(plantRoomList) {
                PlantRoomCard(
                    userName = userName,
                    onNavigationToRoom = onNavigateToRoom,
                    buttonName = it.roomName,
                    plantRoomId = it.plantRoomId,
                    viewModel = viewModel)
            }
        }
    }
}


suspend fun getWeather(latAndLon: String): String {
    var weatherWithCelsiusAndCondition = ""
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

            weatherWithCelsiusAndCondition = "Temperature:" + (airTemp as Double).roundToInt() + "°C \nwith " + clouds

        } else {
            println("HTTPURLCONNECTION_ERROR" + responseCode.toString())
        }

    }.join()

    return weatherWithCelsiusAndCondition
}