package no.hiof.gruppe4.wateryourplants.ui.home

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.Routes
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards

@Composable
fun RoomScreen(userName: String) {
    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO: Add navigation to CreatePlantScreen*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(id = R.string.placeholder_room_name), fontSize = 30.sp)
            PlantCards()
        }
    }
}