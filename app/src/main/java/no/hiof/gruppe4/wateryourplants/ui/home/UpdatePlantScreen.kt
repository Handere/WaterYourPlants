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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import no.hiof.gruppe4.wateryourplants.WindowInfo
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory
import no.hiof.gruppe4.wateryourplants.rememberWindowInfo

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
    val windowInfo = rememberWindowInfo()
    val mContext = LocalContext.current

    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository, plantRoomId = plantRoomId, plantId = plantId))
    val currentPlant = viewModel.currentPlant.observeAsState()

    var species = TextFieldValue(currentPlant.value?.speciesName.toString())
    var speciesLatin = TextFieldValue(currentPlant.value?.speciesLatinName.toString())
    var classification = TextFieldValue(currentPlant.value?.plantClassification.toString())
    var wateringInterval = TextFieldValue(currentPlant.value?.wateringInterval.toString())
    var nutritionInterval = TextFieldValue(currentPlant.value?.nutritionInterval.toString())
    var wateringAndNutritionDay = TextFieldValue(currentPlant.value?.wateringAndNutritionDay.toString())
    var sunRequirement = TextFieldValue(currentPlant.value?.sunRequirement.toString())
    var personalNote = TextFieldValue(currentPlant.value?.note.toString())

    var placeholderSpecies by remember { mutableStateOf(species) }
    var placeholderSpeciesLatin by remember { mutableStateOf(speciesLatin) }
    var placeholderClassification by remember { mutableStateOf(classification) }
    var placeholderWateringInterval by remember { mutableStateOf(wateringInterval) }
    var placeholderNutritionInterval by remember { mutableStateOf(nutritionInterval) }
    var placeholderWateringAndNutritionDay by remember{ mutableStateOf(wateringAndNutritionDay) }
    var placeholderSunRequirement by remember { mutableStateOf(sunRequirement) }
    var placeholderPersonalNote by remember { mutableStateOf(personalNote) }

    LaunchedEffect(key1 = species) {
        placeholderSpecies = species
        placeholderSpeciesLatin = speciesLatin
        placeholderClassification = classification
        placeholderWateringInterval = wateringInterval
        placeholderNutritionInterval = nutritionInterval
        placeholderWateringAndNutritionDay = wateringAndNutritionDay
        placeholderSunRequirement = sunRequirement
        placeholderPersonalNote = personalNote
    }

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
                species = placeholderSpecies,
                speciesLatin = placeholderSpeciesLatin,
                classification = placeholderClassification,
                wateringInterval = placeholderWateringInterval,
                nutritionInterval = placeholderNutritionInterval,
                wateringAndNutritionDay = placeholderWateringAndNutritionDay,
                sunRequirement = placeholderSunRequirement,
                personalNote = placeholderPersonalNote

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
            Text(text = stringResource(id = R.string.update_plant_title), fontSize = 30.sp)

            Row(modifier = modifier.fillMaxWidth()) {
                if (windowInfo.screenWithInfo is WindowInfo.WindowType.Medium || windowInfo.screenWithInfo is WindowInfo.WindowType.Expanded) {
                    // Image
                    Image(
                        painter = painterResource(id = photoUrl),
                        contentDescription = "Placeholder image",
                        modifier = modifier
                            .clip(CircleShape)
                            .border(1.5.dp, Color.Black, CircleShape)
                            .clickable { /*TODO: Add uploading functionality*/ })
                }
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

                    if (windowInfo.screenWithInfo is WindowInfo.WindowType.Compact) {
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
                    }

                    // Species
                    item {
                        Spacer(modifier = modifier.height(20.dp))
                        TextField(
                            label = { Text(text = stringResource(id = R.string.plant_species)) },
                            value = placeholderSpecies,
                            onValueChange = { placeholderSpecies = it },
                            singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
                    }

                    // Species Latin
                    item {
                        Spacer(modifier = modifier.height(20.dp))
                        TextField(
                            label = { Text(text = stringResource(id = R.string.add_new_plant_plant_species_latin)) },
                            value = placeholderSpeciesLatin,
                            onValueChange = { placeholderSpeciesLatin = it },
                            singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
                    }

                    // Classification
                    item {
                        Spacer(modifier = modifier.height(20.dp))
                        TextField(
                            label = { Text(text = stringResource(id = R.string.add_new_plant_plant_classification)) },
                            value = placeholderClassification,
                            onValueChange = { placeholderClassification = it },
                            singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
                    }

                    // Watering interval
                    item {
                        Spacer(modifier = modifier.height(20.dp))
                        TextField(
                            label = { Text(text = stringResource(id = R.string.add_new_plant_plant_watering_interval)) },
                            value = placeholderWateringInterval,
                            onValueChange = { placeholderWateringInterval = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                            singleLine = true // TODO: Bug: Is still possible to press "enter" and get multiple lines
                        )
                    }

                    // Nutrition interval
                    item {
                        Spacer(modifier = modifier.height(20.dp))
                        TextField(
                            label = { Text(text = stringResource(id = R.string.add_new_plant_plant_nutrition_interval)) },
                            value = placeholderNutritionInterval,
                            onValueChange = { placeholderNutritionInterval = it },
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
                            value = placeholderSunRequirement,
                            onValueChange = { placeholderSunRequirement = it },
                            singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
                    }

                    // Personal note
                    item {
                        Spacer(modifier = modifier.height(20.dp))
                        TextField(
                            label = { Text(text = stringResource(id = R.string.add_new_plant_plant_personal_note)) },
                            value = placeholderPersonalNote,
                            onValueChange = { placeholderPersonalNote = it },
                            singleLine = true, // TODO: Bug: Is still possible to press "enter" and get multiple lines
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { updatePlant(
                                viewModel = viewModel,
                                mContext = mContext,
                                popBackStack = popBackStack,
                                plantId = plantId,
                                plantRoomId = plantRoomId,
                                photoUrl = photoUrl,
                                species = placeholderSpecies,
                                speciesLatin = placeholderSpeciesLatin,
                                classification = placeholderClassification,
                                wateringInterval = placeholderWateringInterval,
                                nutritionInterval = placeholderNutritionInterval,
                                wateringAndNutritionDay = placeholderWateringAndNutritionDay,
                                sunRequirement = placeholderSunRequirement,
                                personalNote = placeholderPersonalNote

                            ) })
                        )
                    }
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
    species: TextFieldValue,
    speciesLatin: TextFieldValue,
    classification: TextFieldValue,
    wateringInterval: TextFieldValue,
    nutritionInterval: TextFieldValue,
    wateringAndNutritionDay: TextFieldValue,
    sunRequirement: TextFieldValue,
    personalNote: TextFieldValue,

    ) {
    // TODO: Add more specific input validation and handling
    val errorMessage = "All fields are required" // Can't use getString() or stringResource() because it's not Composable
    if (species.text.isEmpty() ||
        speciesLatin.text.isEmpty() ||
        classification.text.isEmpty() ||
        wateringInterval.text.isEmpty() ||
        nutritionInterval.text.isEmpty() ||
        wateringAndNutritionDay.text.isEmpty() ||
        sunRequirement.text.isEmpty() ||
        personalNote.text.isEmpty()
    ) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show()
    }
    else {
        viewModel.updatePlant(
            plantId = plantId,
            roomId = plantRoomId,
            speciesName = species.text,
            speciesLatinName = speciesLatin.text,
            plantClassification = classification.text,
            photoUrl = photoUrl,
            wateringInterval = wateringInterval.text.toInt(),
            nutritionInterval = nutritionInterval.text.toInt(),
            wateringAndNutritionDay = wateringAndNutritionDay.text,
            sunRequirement = sunRequirement.text,
            note = personalNote.text
        )
        popBackStack()
    }
}