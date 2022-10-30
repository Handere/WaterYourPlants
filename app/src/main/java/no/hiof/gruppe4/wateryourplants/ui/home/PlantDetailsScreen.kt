package no.hiof.gruppe4.wateryourplants.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import androidx.compose.ui.text.style.TextAlign
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

        Column(modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally) {
                TopAppBar( modifier = modifier.fillMaxWidth()) {
                    Text(text = currentPlantRoom?.roomName?.uppercase().toString(),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Left,
                       )
                }

                Image(
                    painter = painterResource(id = R.drawable.no_plant_image),
                    contentDescription = plantDetails.value?.speciesName,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxWidth(0.5f),
                    alignment = Alignment.Center
                )

                Row(){
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = plantDetails.value?.speciesName.toString(),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = "(" + plantDetails.value?.speciesLatinName.toString() + ")",
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )}

                Row(){
                    //TODO: Symbols for nutrition, watering and sun conditions
                }

                Text(text = plantDetails.value?.note.toString())
        }
    }
}