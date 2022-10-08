package no.hiof.gruppe4.wateryourplants.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards
import no.hiof.gruppe4.wateryourplants.ui.theme.WaterYourPlantsTheme
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.ui.theme.Shapes

class NewPlantActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterYourPlantsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = { ScaffoldTopAppBar() },
                        floatingActionButton = {
                            FloatingActionButton(onClick = { /*TODO: Add create functionality*/ }) {
                                Icon(imageVector = Icons.Default.Create, contentDescription = "Create")
                            }
                        },
                        content = { AddNewPlant() }
                    )
                }
            }
        }
    }
}

@Composable
fun AddNewPlant(modifier: Modifier = Modifier, painter: Painter = painterResource(id = R.drawable.no_plant_image)) {
    val localContext = LocalContext.current
    val plantSearch = remember { mutableStateOf(TextFieldValue())}
    val species = remember { mutableStateOf(TextFieldValue())}
    val speciesLatin = remember { mutableStateOf(TextFieldValue())}
    val classification = remember { mutableStateOf(TextFieldValue())}
    val age = remember { mutableStateOf(TextFieldValue())}
    val wateringInterval = remember { mutableStateOf(TextFieldValue())}
    val nutritionInterval = remember { mutableStateOf(TextFieldValue())}
    val sunRequirement = remember { mutableStateOf(TextFieldValue())}
    val personalNote = remember { mutableStateOf(TextFieldValue())}

    Column(modifier = modifier.fillMaxWidth().padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.add_new_plant), fontSize = 30.sp)
        LazyColumn(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            item {
                // Plant search field
                TextField(
                    label ={ Text(text = stringResource(id = R.string.add_new_plant_search_field))},
                    value = plantSearch.value,
                    onValueChange = { plantSearch.value = it })

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
                    painter = painter,
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
                    label = {Text(text = stringResource(id = R.string.plant_species))},
                    value = species.value,
                    onValueChange = { species.value = it })
            }

            // Species Latin
            item {
                Spacer(modifier = modifier.height(20.dp))
                TextField(
                    label = {Text(text = stringResource(id = R.string.add_new_plant_plant_species_latin))},
                    value = speciesLatin.value,
                    onValueChange = { speciesLatin.value = it })
            }

            // Classification
            item {
                Spacer(modifier = modifier.height(20.dp))
                TextField(
                    label = {Text(text = stringResource(id = R.string.add_new_plant_plant_classification))},
                    value = classification.value,
                    onValueChange = { classification.value = it })
            }

            // Age
            item {
                Spacer(modifier = modifier.height(20.dp))
                TextField(
                    label = {Text(text = stringResource(id = R.string.add_new_plant_plant_age))},
                    value = age.value,
                    onValueChange = { age.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            }

            // Watering interval
            item {
                Spacer(modifier = modifier.height(20.dp))
                TextField(
                    label = {Text(text = stringResource(id = R.string.add_new_plant_plant_watering_interval))},
                    value = wateringInterval.value,
                    onValueChange = { wateringInterval.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            // Nutrition interval
            item {
                Spacer(modifier = modifier.height(20.dp))
                TextField(
                    label = {Text(text = stringResource(id = R.string.add_new_plant_plant_nutrition_interval))},
                    value = nutritionInterval.value,
                    onValueChange = { nutritionInterval.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            }

            // Sun requirement
            item {
                Spacer(modifier = modifier.height(20.dp))
                TextField(
                    label = {Text(text = stringResource(id = R.string.add_new_plant_plant_sun_requirement))},
                    value = sunRequirement.value,
                    onValueChange = { sunRequirement.value = it })
            }

            // Personal note
            item {
                Spacer(modifier = modifier.height(20.dp))
                TextField(
                    label = {Text(text = stringResource(id = R.string.add_new_plant_plant_personal_note))},
                    value = personalNote.value,
                    onValueChange = { personalNote.value = it })
            }
        }
    }
}

@Composable
fun Greeting3(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    WaterYourPlantsTheme {
        Greeting3("Android")
    }
}