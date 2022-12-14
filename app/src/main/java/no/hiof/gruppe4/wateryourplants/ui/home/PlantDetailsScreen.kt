package no.hiof.gruppe4.wateryourplants.ui.home


import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WindowInfo
import no.hiof.gruppe4.wateryourplants.data.Plant
import no.hiof.gruppe4.wateryourplants.rememberWindowInfo
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

@Composable
fun PlantDetailsScreen(
    onNavigateToUpdatePlantScreen: (Int, String, Int) -> Unit,
    popBackStack: () -> Unit,
    userName: String?,
    plantRoomId: Int,
    plantId: Int,
    modifier: Modifier = Modifier
) {

    val viewModel: PlantViewModel = viewModel(
        factory = PlantViewModelFactory(
            (LocalContext.current.applicationContext as WaterYourPlantsApplication).repository,
            plantRoomId = plantRoomId,
            plantId = plantId
        )
    )

    val windowInfo = rememberWindowInfo()
    val currentPlant = viewModel.currentPlant.observeAsState()

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        DeletePlantDialog(
            popBackStack = popBackStack,
            openDialog = openDialog,
            viewModel = viewModel,
            plant = currentPlant.value!!
        )
    }

    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigateToUpdatePlantScreen(plantId, userName.toString(), plantRoomId)
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    ) { padding ->

        RequestNotificationPermission()

        Row(modifier = modifier.fillMaxWidth()) {

            if (windowInfo.screenWithInfo is WindowInfo.WindowType.Medium || windowInfo.screenWithInfo is WindowInfo.WindowType.Expanded) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current.applicationContext)
                        .error(R.drawable.no_plant_image)
                        .data(currentPlant.value?.photoUrl)
                        .build(),
                    contentDescription = currentPlant.value?.speciesName,
                    contentScale = ContentScale.Fit,
                    modifier = modifier.fillMaxWidth(0.5f),
                    alignment = Alignment.Center
                )
            }
                LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (windowInfo.screenWithInfo is WindowInfo.WindowType.Compact) {
                    item {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current.applicationContext)
                                .error(R.drawable.no_plant_image)
                                .data(currentPlant.value?.photoUrl)
                                .build(),
                            contentDescription = currentPlant.value?.speciesName,
                            contentScale = ContentScale.Fit,
                            modifier = modifier.fillMaxWidth(0.5f),
                            alignment = Alignment.Center
                        )
                    }
                }

                // Plant name
                item {
                    Row() {
                        Spacer(modifier = modifier.height(5.dp))
                        Text(
                            text = currentPlant.value?.speciesName.toString(),
                            modifier = modifier.padding(5.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = modifier.height(5.dp))
                        Text(
                            text = "(" + currentPlant.value?.speciesLatinName.toString() + ")",
                            modifier = modifier.padding(5.dp),
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }

                // Watering interval
                item {
                    PlantDetail(
                        detailTitle = stringResource(id = R.string.add_new_plant_plant_watering_interval),
                        detail = currentPlant.value?.wateringInterval.toString() + " days",
                        modifier = modifier
                    )
                }

                // Last watering day
                item {
                    PlantDetail(
                        detailTitle = stringResource(id = R.string.plant_details_last_watering_day),
                        detail = currentPlant.value?.lastWateringDate.toString(),
                        modifier = modifier
                    )
                }

                // Next watering day
                item {
                    PlantDetail(
                        detailTitle = stringResource(id = R.string.plant_details_next_watering_day),
                        detail = currentPlant.value?.nextWateringDate.toString(),
                        modifier = modifier
                    )
                }

                // Classification
                item {
                    PlantDetail(
                        detailTitle = stringResource(id = R.string.add_new_plant_plant_classification),
                        detail = currentPlant.value?.plantClassification.toString(),
                        modifier = modifier
                    )
                }

                // Sun requirements
                item {
                    PlantDetail(
                        detailTitle = stringResource(id = R.string.add_new_plant_plant_sun_requirement),
                        detail = currentPlant.value?.sunRequirement.toString(),
                        modifier = modifier
                    )
                }

                // Note
                item {
                    PlantDetail(
                        detailTitle = stringResource(id = R.string.add_new_plant_plant_personal_note),
                        detail = currentPlant.value?.note.toString(),
                        modifier = modifier
                    )
                }


                // Water now button
                item {
                    Row() {
                        Button(
                            onClick = {
                                viewModel.updateWateringDate(
                                    currentPlant.value?.wateringInterval!!,
                                    plantId
                                )
                            },
                            shape = Shapes.medium,
                            modifier = modifier.height(50.dp)
                        ) {
                            Text(text = stringResource(id = R.string.water_now_button))
                        }
                    }
                }

                // Delete button
                item {
                    Spacer(modifier = modifier.height(5.dp))
                    ClickableText(
                        text = AnnotatedString(stringResource(id = R.string.delete_plant)),
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
                }
            }
        }
    }
}

@Composable
fun RequestNotificationPermission() {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { _ ->}
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        SideEffect {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@Composable
fun PlantDetail(detailTitle: String, detail: String, modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "$detailTitle:",
            modifier = modifier
                .padding(5.dp)
                .fillMaxWidth(0.5f),
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(text = detail,
            modifier = modifier
                .padding(5.dp)
                .fillMaxWidth(),
            fontSize = 18.sp
        )
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