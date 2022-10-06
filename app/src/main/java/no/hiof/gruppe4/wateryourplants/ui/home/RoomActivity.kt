package no.hiof.gruppe4.wateryourplants.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards
import no.hiof.gruppe4.wateryourplants.ui.components.PlantRoomCard
import no.hiof.gruppe4.wateryourplants.ui.theme.WaterYourPlantsTheme

class RoomActivity : ComponentActivity() {
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
                        content = { PlantCards() })
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    WaterYourPlantsTheme {
        Greeting2("Android")
    }
}