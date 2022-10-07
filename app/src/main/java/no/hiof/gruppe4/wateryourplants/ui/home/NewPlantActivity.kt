package no.hiof.gruppe4.wateryourplants.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
                        content = { AddNewPlant() }
                    )
                }
            }
        }
    }
}

@Composable
fun AddNewPlant(modifier: Modifier = Modifier, painter: Painter = painterResource(id = R.drawable.no_plant_image)) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        val localContext = LocalContext.current
        val plantSearch = remember { mutableStateOf(TextFieldValue())}
        val species = remember { mutableStateOf(TextFieldValue())}
        var wateringInterval by remember { mutableStateOf(0)}

        Text(text = stringResource(id = R.string.add_new_plant), fontSize = 30.sp)
        Spacer(modifier = modifier.height(20.dp))
        TextField(
            label ={ Text(text = stringResource(id = R.string.add_new_plant_search_field))},
            value = plantSearch.value,
            onValueChange = { plantSearch.value = it })

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

        Spacer(modifier = modifier.height(20.dp))
        Image(
            painter = painter,
            contentDescription = "Placeholder image",
            modifier = modifier
                .clip(CircleShape)
                .border(1.5.dp, Color.Black, CircleShape))

        Spacer(modifier = modifier.height(20.dp))
        TextField(
            label = {Text(text = stringResource(id = R.string.plant_species))},
            value = species.value,
            onValueChange = { species.value = it })

        Spacer(modifier = modifier.height(20.dp))

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