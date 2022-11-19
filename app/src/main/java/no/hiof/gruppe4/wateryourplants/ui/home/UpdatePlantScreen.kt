package no.hiof.gruppe4.wateryourplants.ui.home

import android.content.Context
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory

@RequiresApi(value = 26)
@Composable
fun UpdatePlantScreen(
    plantId: Int,
    userName: String?,
    plantRoomId: Int,
    modifier: Modifier = Modifier,
    photoUrl: Int = R.drawable.no_plant_image,
    popBackStack: () -> Unit
) {

    val mContext = LocalContext.current

    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository, plantRoomId = plantRoomId, plantId = plantId))
    val currentPlant = viewModel.currentPlant.observeAsState()
    println("Plant: \n" + currentPlant + "\n :end")

    val species = remember { mutableStateOf(TextFieldValue(currentPlant.value?.speciesName.toString())) }
    val speciesLatin = remember { mutableStateOf(TextFieldValue(currentPlant.value?.speciesLatinName.toString())) }
    val classification = remember { mutableStateOf(TextFieldValue(currentPlant.value?.plantClassification.toString())) }
    val wateringInterval = remember { mutableStateOf(TextFieldValue(currentPlant.value?.wateringInterval.toString())) }
    val nutritionInterval = remember { mutableStateOf(TextFieldValue(currentPlant.value?.nutritionInterval.toString())) }
    val wateringAndNutritionDay = remember{ mutableStateOf(TextFieldValue(" ")) }
    val sunRequirement = remember { mutableStateOf(TextFieldValue(currentPlant.value?.sunRequirement.toString())) }
    val personalNote = remember { mutableStateOf(TextFieldValue(currentPlant.value?.note.toString())) }

    Scaffold(
        topBar = { ScaffoldTopAppBar(userName)},
        floatingActionButton = {
            FloatingActionButton(onClick = { updatePlant(
                viewModel = viewModel,
                mContext = mContext,
                popBackStack = popBackStack,
                plantId = plantId,
                plantRoomId = plantRoomId,
                photoUrl = photoUrl,
                species = species,
                speciesLatin = speciesLatin,
                classification = classification,
                wateringInterval = wateringInterval,
                nutritionInterval = nutritionInterval,
                wateringAndNutritionDay = wateringAndNutritionDay,
                sunRequirement = sunRequirement,
                personalNote = personalNote

            ) })
            {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
            }
        }
    ) { padding -> // TODO: dafuq do we need this thing?

        Box(modifier = modifier.padding(padding))
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(id = R.string.add_new_plant), fontSize = 30.sp)

            // TODO: Make DRY...
            LazyColumn(modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                /*item {
                    // Plant search field
                    TextField(
                        label ={ Text(text = stringResource(id = R.string.add_new_plant_search_field)) },
                        value = plantSearch.value,
                        onValueChange = { plantSearch.value = it }
                    )

                    // Plant search buttons
                    Spacer(modifier = modifier.height(20.dp))
                    Row(modifier = modifier) {
                        Button(onClick = { *//*TODO*//* },
                            shape = Shapes.medium,
                            modifier = modifier.height(50.dp)
                        ) {
                            Text(text = stringResource(id = R.string.add_new_plant_image_search_button))
                        }
                        Spacer(modifier = modifier.width(20.dp))
                        Button(onClick = { *//*TODO*//* },
                            shape = Shapes.medium,
                            modifier = modifier.height(50.dp)
                        ) {
                            Text(text = stringResource(id = R.string.add_new_plant_search_button))
                        }
                    }
                }*/

                // Image
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    Image(
                        painter = painterResource(id = photoUrl),
                        contentDescription = "Placeholder image",
                        modifier = modifier
                            .clip(CircleShape)
                            .border(1.5.dp, Color.Black, CircleShape)
                            .clickable { /*TODO: Add uploading functionality*/ })
                }

                // Species
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.plant_species)) },
                        value = species.value,
                        onValueChange = { species.value = it },
                        singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
                }

                // Species Latin
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_species_latin)) },
                        value = speciesLatin.value,
                        onValueChange = { speciesLatin.value = it },
                        singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
                }

                // Classification
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_classification)) },
                        value = classification.value,
                        onValueChange = { classification.value = it },
                        singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
                }

                // Watering interval
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_watering_interval)) },
                        value = wateringInterval.value,
                        onValueChange = { wateringInterval.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        singleLine = true // TODO: Bug: Is still possible to press "enter" and get multiple lines
                    )
                }

                // Nutrition interval
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_nutrition_interval)) },
                        value = nutritionInterval.value,
                        onValueChange = { nutritionInterval.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        singleLine = true // TODO: Bug: Is still possible to press "enter" and get multiple lines
                    )
                }

                //watering and nutrition day
                /*
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_watering_and_nutrition_day)) },
                        value = wateringAndNutritionDay.value,
                        onValueChange = { wateringAndNutritionDay.value = it },
                        singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
                }
                 */

                // Sun requirement
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_sun_requirement)) },
                        value = sunRequirement.value,
                        onValueChange = { sunRequirement.value = it },
                        singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
                }

                // Personal note
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_personal_note)) },
                        value = personalNote.value,
                        onValueChange = { personalNote.value = it },
                        singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { updatePlant(
                            viewModel = viewModel,
                            mContext = mContext,
                            popBackStack = popBackStack,
                            plantId = plantId,
                            plantRoomId = plantRoomId,
                            photoUrl = photoUrl,
                            species = species,
                            speciesLatin = speciesLatin,
                            classification = classification,
                            wateringInterval = wateringInterval,
                            nutritionInterval = nutritionInterval,
                            wateringAndNutritionDay = wateringAndNutritionDay,
                            sunRequirement = sunRequirement,
                            personalNote = personalNote

                        ) })
                    )
                }
            }
        }
    }
}

@RequiresApi(value = 26)
fun updatePlant(
    viewModel: PlantViewModel,
    mContext: Context,
    popBackStack: () -> Unit,
    plantId: Int,
    plantRoomId: Int,
    photoUrl: Int,
    species: MutableState<TextFieldValue>,
    speciesLatin: MutableState<TextFieldValue>,
    classification: MutableState<TextFieldValue>,
    wateringInterval: MutableState<TextFieldValue>,
    nutritionInterval: MutableState<TextFieldValue>,
    wateringAndNutritionDay: MutableState<TextFieldValue>,
    sunRequirement: MutableState<TextFieldValue>,
    personalNote: MutableState<TextFieldValue>,

    ) {
    // TODO: Add more specific input validation and handling
    val errorMessage = "All fields are required" // Can't use getString() or stringResource() because it's not Composable
    if (species.value.text.isEmpty() ||
        speciesLatin.value.text.isEmpty() ||
        classification.value.text.isEmpty() ||
        wateringInterval.value.text.isEmpty() ||
        nutritionInterval.value.text.isEmpty() ||
        wateringAndNutritionDay.value.text.isEmpty() ||
        sunRequirement.value.text.isEmpty() ||
        personalNote.value.text.isEmpty()
    ) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show()
    }
    else {
        viewModel.updatePlant(
            plantId = plantId,
            roomId = plantRoomId,
            speciesName = species.value.text,
            speciesLatinName = speciesLatin.value.text,
            plantClassification = classification.value.text,
            photoUrl = photoUrl,
            wateringInterval = wateringInterval.value.text.toInt(),
            nutritionInterval = nutritionInterval.value.text.toInt(),
            wateringAndNutritionDay = wateringAndNutritionDay.value.text,
            sunRequirement = sunRequirement.value.text,
            note = personalNote.value.text
        )
        popBackStack()
    }
}