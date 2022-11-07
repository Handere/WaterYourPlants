package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

@RequiresApi(value = 26)
@Composable
fun PlantRoomCard(
    onNavigationToRoom: (String, Int) -> Unit,
    userName: String?,
    buttonName: String,
    plantRoomId: Int,
    viewModel1: PlantViewModel,
    modifier: Modifier = Modifier) {

    val plantRoomPlantList by viewModel1.getPlantRoomPlantList(plantRoomId).observeAsState(listOf())
    val notifications = viewModel1.numberOfNotifyingPlants(plantRoomPlantList)

    Button(onClick = { onNavigationToRoom(userName.toString(), plantRoomId) },
        shape = Shapes.medium,
        modifier = modifier.fillMaxWidth()) {
            Text(text = buttonName.uppercase())
            if (notifications > 0) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                Text(text = notifications.toString())
            }
    }
}