package no.hiof.gruppe4.wateryourplants.ui.home

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import no.hiof.gruppe4.wateryourplants.data.Plant
import no.hiof.gruppe4.wateryourplants.data.PlantRoom
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

// TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
@RequiresApi(value = 26)
@Composable
fun PlantDetailsScreen(
    popBackStack: () -> Unit,
    userName: String?,
    plantRoomId: Int,
    plantId: Int,
    modifier: Modifier = Modifier
) {
    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository, plantRoomId = plantRoomId, plantId = plantId))

    val currentPlantRoom by viewModel.currentPlantRoom.observeAsState()
    val currentPlant = viewModel.currentPlant.observeAsState()

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        DeletePlantDialog(
            popBackStack = popBackStack,
            openDialog = openDialog,
            viewModel = viewModel,
            plant = currentPlant.value!!)
    }

    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    ) {padding ->

        Column(modifier = modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
                TopAppBar( modifier = modifier.fillMaxWidth()) {
                    Text(text = currentPlantRoom?.roomName?.uppercase().toString(),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Left,
                       )
                }

                Image(
                    painter = painterResource(id = R.drawable.no_plant_image),
                    contentDescription = currentPlant.value?.speciesName,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxWidth(0.5f),
                    alignment = Alignment.Center
                )

                // Plant name
                Row(){
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = currentPlant.value?.speciesName.toString(),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = "(" + currentPlant.value?.speciesLatinName.toString() + ")",
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )}

                // Watering interval
                Row(){
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = stringResource(id = R.string.add_new_plant_plant_watering_interval) + ":",
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = currentPlant.value?.wateringInterval.toString() + " days",
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )
                }

                // Last watering day
                Row(){
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = stringResource(id = R.string.plant_details_last_watering_day),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = currentPlant.value?.lastWateringDate.toString(),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )}

                // Next watering day
                Row(){
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = stringResource(id = R.string.plant_details_next_watering_day),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = currentPlant.value?.nextWateringDate.toString(),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )}

                // Classification
                Row(){
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = stringResource(id = R.string.add_new_plant_plant_classification) + ":",
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = currentPlant.value?.plantClassification.toString(),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )}

                // Sun requirements
                Row(){
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = stringResource(id = R.string.add_new_plant_plant_sun_requirement) + ":",
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = currentPlant.value?.sunRequirement.toString(),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )}

                // Note
                Row(){
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = stringResource(id = R.string.add_new_plant_plant_personal_note) + ":",
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(text = currentPlant.value?.note.toString(),
                        modifier = modifier.padding(5.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )}

            // Water now button
            Row(){
                Button(onClick = { viewModel.updateWateringDate(currentPlant.value?.wateringInterval!!, plantId) },
                    shape = Shapes.medium,
                    modifier = modifier.height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.water_now_button))
                }
            }
            Spacer(modifier = modifier.height(5.dp))
            Button(onClick = {
                if (!openDialog.value) {
                    openDialog.value = true
                }
            }) {
                Text(text = stringResource(id = R.string.delete_plant))
            }
        }
    }
}

@Composable
fun DeletePlantDialog(
    popBackStack: () -> Unit,
    openDialog: MutableState<Boolean>,
    viewModel: PlantViewModel,
    plant: Plant,) {

    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        title = { Text(text = stringResource(id = R.string.delete_plant) + "?") },
        text = { Text(text = stringResource(id = R.string.delete_plant_info)) },
        confirmButton = { TextButton(
            onClick = {
                openDialog.value = false
                viewModel.deletePlant(plant)
                popBackStack()
            }
        ) {
            Text(stringResource(id = R.string.confirm))
        } },
        dismissButton = { TextButton(onClick = { openDialog.value = false }) {
            Text(stringResource(id = R.string.dismiss))
        } }
    )
}