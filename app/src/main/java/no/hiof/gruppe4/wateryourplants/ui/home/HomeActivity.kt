package no.hiof.gruppe4.wateryourplants.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import no.hiof.gruppe4.wateryourplants.model.plantRoom
import no.hiof.gruppe4.wateryourplants.model.plants
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCard
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards
import no.hiof.gruppe4.wateryourplants.ui.components.PlantRoomCard
import no.hiof.gruppe4.wateryourplants.ui.theme.WaterYourPlantsTheme

class HomeActivity : ComponentActivity() {
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
                        topBar = { TopAppBar() {}},
                        content = {
                            RoomCards()
                        })


                }

            }
        }
    }
}

@Composable
fun RoomCards() {

    LazyColumn {
        items(plantRoom) { // TODO: Change to list from view model
            PlantRoomCard(
                buttonName = it.name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    WaterYourPlantsTheme {
        Greeting("Android")
    }
}