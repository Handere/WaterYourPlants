package no.hiof.gruppe4.wateryourplants.ui.components

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
import androidx.compose.ui.res.stringResource
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

@Composable
fun PlantRoomCard(
    onNavigationToRoom: (String, Int) -> Unit,
    userName: String?,
    buttonName: String,
    plantRoomId: Int,
    viewModel: PlantViewModel,
    modifier: Modifier = Modifier) {

    val plantRoomPlantList by viewModel.getPlantRoomPlantList(plantRoomId).observeAsState(listOf())
    val notifications = viewModel.numberOfNotifyingPlants(plantRoomPlantList)

    Button(onClick = { onNavigationToRoom(userName.toString(), plantRoomId) },
        shape = Shapes.medium,
        modifier = modifier.fillMaxWidth()) {
            Text(text = buttonName.uppercase())
            if (notifications > 0) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = stringResource(id = R.string.notifications))
                Text(text = notifications.toString())
            }
    }
}