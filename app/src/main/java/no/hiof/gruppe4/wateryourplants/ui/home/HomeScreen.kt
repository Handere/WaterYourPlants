package no.hiof.gruppe4.wateryourplants.ui.home

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
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.data.PlantRoom
import no.hiof.gruppe4.wateryourplants.home.*
import no.hiof.gruppe4.wateryourplants.ui.components.PlantRoomCard

@Composable
fun HomeScreen(
    onNavigateToRoom: (String, Int) -> Unit,
    userName: String?
) {
    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository))

    val plantRoomList by viewModel.plantRoomList.observeAsState(listOf())

    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO: Add functionality*/ }) {
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
        Text(text = stringResource(id = R.string.header_room), modifier.fillMaxWidth(),
            fontSize = 30.sp)
        LazyColumn {
            items(plantRoomList) { // TODO: Change to list from view model
                PlantRoomCard(
                    userName = userName,
                    onNavigationToRoom = onNavigateToRoom,
                    buttonName = it.roomName,
                    plantRoomId = it.plantRoomId )
            }
        }
    }
}