package no.hiof.gruppe4.wateryourplants.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.home.PlantViewModel
import no.hiof.gruppe4.wateryourplants.home.PlantViewModelFactory
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

@Composable
fun CreatePlantScreen(
    userName: String?,
    plantRoomId: Int,
    modifier: Modifier = Modifier,
    photoUrl: Int = R.drawable.no_plant_image,
    popBackStack: () -> Unit
) {

    val mContext = LocalContext.current

    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository, plantRoomId = plantRoomId))

    val plantSearch = remember { mutableStateOf(TextFieldValue()) }
    val species = remember { mutableStateOf(TextFieldValue()) }
    val speciesLatin = remember { mutableStateOf(TextFieldValue()) }
    val classification = remember { mutableStateOf(TextFieldValue()) }
    val wateringInterval = remember { mutableStateOf(TextFieldValue()) }
    val nutritionInterval = remember { mutableStateOf(TextFieldValue()) }
    val wateringAndNutritionDay = remember{ mutableStateOf(TextFieldValue()) }
    val sunRequirement = remember { mutableStateOf(TextFieldValue()) }
    val personalNote = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = { ScaffoldTopAppBar(userName)},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // TODO: Add more specific input validation and handling
                val errorMessage = "All fields are required"
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
                    viewModel.insertPlant(
                        plantRoomId,
                        species.value.text,
                        speciesLatin.value.text,
                        classification.value.text,
                        photoUrl,
                        wateringInterval.value.text.toInt(),
                        nutritionInterval.value.text.toInt(),
                        wateringAndNutritionDay.value.text,
                        sunRequirement.value.text,
                        personalNote.value.text
                    )
                    popBackStack()
                }
            })
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

                item {
                    // Plant search field
                    TextField(
                        label ={ Text(text = stringResource(id = R.string.add_new_plant_search_field)) },
                        value = plantSearch.value,
                        onValueChange = { plantSearch.value = it }
                    )

                    // Plant search buttons
                    Spacer(modifier = modifier.height(20.dp))
                    Row(modifier = modifier) {
                        Button(onClick = { /*TODO*/ },
                            shape = Shapes.medium,
                            modifier = modifier.height(50.dp)
                        ) {
                            Text(text = stringResource(id = R.string.add_new_plant_image_search_button))
                        }
                        Spacer(modifier = modifier.width(20.dp))
                        Button(onClick = { /*TODO*/ },
                            shape = Shapes.medium,
                            modifier = modifier.height(50.dp)
                        ) {
                            Text(text = stringResource(id = R.string.add_new_plant_search_button))
                        }
                    }
                }

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
                        onValueChange = { species.value = it })
                }

                // Species Latin
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_species_latin)) },
                        value = speciesLatin.value,
                        onValueChange = { speciesLatin.value = it })
                }

                // Classification
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_classification)) },
                        value = classification.value,
                        onValueChange = { classification.value = it })
                }

                // Watering interval
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_watering_interval)) },
                        value = wateringInterval.value,
                        onValueChange = { wateringInterval.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                // Nutrition interval
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_nutrition_interval)) },
                        value = nutritionInterval.value,
                        onValueChange = { nutritionInterval.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                //watering and nutrition day
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_watering_and_nutrition_day)) },
                        value = wateringAndNutritionDay.value,
                        onValueChange = { wateringAndNutritionDay.value = it })
                }

                // Sun requirement
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_sun_requirement)) },
                        value = sunRequirement.value,
                        onValueChange = { sunRequirement.value = it })
                }

                // Personal note
                item {
                    Spacer(modifier = modifier.height(20.dp))
                    TextField(
                        label = { Text(text = stringResource(id = R.string.add_new_plant_plant_personal_note)) },
                        value = personalNote.value,
                        onValueChange = { personalNote.value = it })
                }
            }
        }
    }
}