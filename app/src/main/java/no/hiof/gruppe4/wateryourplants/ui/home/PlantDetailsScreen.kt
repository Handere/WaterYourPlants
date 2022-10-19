package no.hiof.gruppe4.wateryourplants.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards

@Composable
fun PlantDetailsScreen(
    userName: String?,
    plantRoomId: Int,
    plantId: Int,
    modifier: Modifier = Modifier
) {
    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository, plantRoomId = plantRoomId, plantId = plantId))

    val currentPlantRoom by viewModel.currentPlantRoom.observeAsState()
    val plantDetails = viewModel.plantDetails.observeAsState()

    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    ) {padding ->

        Box(modifier = modifier.padding(padding)
            .height(200.dp)) {
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.no_plant_image),
                    contentDescription = plantDetails.value?.speciesName,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxWidth(0.5f))
                Column(modifier = modifier
                    .fillMaxHeight()) {
                    Text(text = currentPlantRoom?.roomName?.uppercase().toString(), fontSize = 30.sp)
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = plantDetails.value?.speciesName.toString(),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = plantDetails.value?.speciesLatinName.toString(),
                        fontSize = 14.sp,
                        modifier = modifier.padding(5.dp),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}