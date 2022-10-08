package no.hiof.gruppe4.wateryourplants.ui.home

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.model.plantRoom
import no.hiof.gruppe4.wateryourplants.ui.components.PlantRoomCard

@Composable
fun HomeScreen(
    onNavigateToRoom: () -> Unit,
    userName: String?
) {
    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO: Add functionality*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        RoomCards(onNavigateToRoom)
    }


}

@Composable
fun ScaffoldTopAppBar(userName: String?) {
    TopAppBar(title = { userName?.let { Text(text = it) } }) // TODO: Placeholder. Change to actual username
}

@Composable
fun RoomCards(
    onNavigateToRoom: () -> Unit,
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
            items(plantRoom) { // TODO: Change to list from view model
                PlantRoomCard(
                    onNavigateToRoom,
                    buttonName = it.name)
            }
        }
    }
}