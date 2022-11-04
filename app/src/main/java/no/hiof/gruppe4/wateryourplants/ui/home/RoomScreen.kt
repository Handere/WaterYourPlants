package no.hiof.gruppe4.wateryourplants.ui.home

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.home.*
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.data.Plant
import no.hiof.gruppe4.wateryourplants.data.PlantRoom
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCard

// TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
@RequiresApi(value = 26)
@Composable
fun RoomScreen(
    onNavigationToCreatePlant: (String, Int) -> Unit,
    onNavigationToPlantDetails: (String, Int, Int) -> Unit,
    popBackStack: () -> Unit,
    userName: String?,
    plantRoomId: Int,
    modifier: Modifier = Modifier
) {

    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository, plantRoomId = plantRoomId))

    val openDialog = remember { mutableStateOf(false) }

    val plantRoomPlantList by viewModel.plantRoomPlantList.observeAsState(listOf())
    val currentPlantRoom by viewModel.currentPlantRoom.observeAsState()

    if (openDialog.value) {
        DeleteRoomWithPlantsDialog(
            popBackStack = popBackStack,
            openDialog = openDialog,
            viewModel = viewModel,
            currentPlantRoom = currentPlantRoom,
            plantRoomPlantList = plantRoomPlantList)
    }


    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigationToCreatePlant(userName.toString(),
                plantRoomId
            ) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
        ) {padding -> // TODO: ...
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)) {
            Text(text = currentPlantRoom?.roomName?.uppercase().toString(), fontSize = 30.sp)
            Spacer(modifier = modifier.height(5.dp))
            LazyColumn() {
                items(plantRoomPlantList) {
                    PlantCard(
                        onNavigationToPlantDetails = onNavigationToPlantDetails,
                        userName = userName,
                        plantRoomId = plantRoomId,
                        plantId = it.plantId,
                        contentDescription = it.speciesName,
                        species = it.speciesName,
                        speciesLatin = it.speciesLatinName,
                        nextWateringDay = it.nextWateringDate)
                }

                // Delete button
                item {
                    Spacer(modifier = modifier.height(5.dp))
                    ClickableText(
                        text = AnnotatedString(stringResource(id = R.string.delete_room_and_plants)) ,
                        onClick = {
                            if (!openDialog.value) {
                                openDialog.value = true
                            }
                        },
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                    Spacer(modifier = modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun DeleteRoomWithPlantsDialog(
    popBackStack: () -> Unit,
    openDialog: MutableState<Boolean>,
    viewModel: PlantViewModel,
    currentPlantRoom: PlantRoom?,
    plantRoomPlantList: List<Plant>) {

    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        title = { Text(text = stringResource(id = R.string.delete_room_and_plants) + "?") },
        text = { Text(stringResource(id = R.string.delete_room_and_plants_info)) },
        confirmButton = { TextButton(
                onClick = {
                    openDialog.value = false
                    viewModel.deletePlantRoomAndPlants(currentPlantRoom!!, plantRoomPlantList)
                    popBackStack()
                }
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = { TextButton(onClick = { openDialog.value = false }) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}
