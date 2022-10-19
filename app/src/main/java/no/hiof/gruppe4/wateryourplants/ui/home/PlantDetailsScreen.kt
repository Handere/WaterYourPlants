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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards

@Composable
fun PlantDetailsScreen(
    onNavigationToCreatePlant: (String, Int, Int) -> Unit,
    userName: String?,
    plantRoomId: Int,
    plantId: Int,
    modifier: Modifier = Modifier
) {
    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository, plantRoomId = plantRoomId))

    val plantRoomPlantList by viewModel.plantRoomPlantList.observeAsState(listOf())
    val currentPlantRoom by viewModel.currentPlantRoom.observeAsState()

    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {padding ->
        Column(modifier = modifier.padding(padding)) {
            Text(text = currentPlantRoom?.roomName?.uppercase().toString(), fontSize = 30.sp)
            Spacer(modifier = modifier.height(5.dp))

        }
    }
}