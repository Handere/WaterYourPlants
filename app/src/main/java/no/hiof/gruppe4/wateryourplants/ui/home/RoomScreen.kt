package no.hiof.gruppe4.wateryourplants.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards

@Composable
fun RoomScreen(
    onNavigationToCreatePlant: (String, String) -> Unit,
    userName: String?,
    roomName: String?,
    modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigationToCreatePlant(userName.toString(),
                roomName.toString()
            ) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Column(modifier = modifier.padding(16.dp, 0.dp)) {
            roomName?.let { it1 -> Text(text = it1.uppercase(), fontSize = 30.sp) }
            Spacer(modifier = modifier.height(5.dp))
            PlantCards()
        }
    }
}