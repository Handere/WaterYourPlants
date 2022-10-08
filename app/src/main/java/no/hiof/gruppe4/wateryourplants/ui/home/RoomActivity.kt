package no.hiof.gruppe4.wateryourplants.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.hiof.gruppe4.wateryourplants.R
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards
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
                        floatingActionButton = { RoomFloatingActionButton() },
                        content = {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = stringResource(id = R.string.placeholder_room_name), fontSize = 30.sp)
                                PlantCards()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RoomFloatingActionButton() {
    val localContext = LocalContext.current
    FloatingActionButton(onClick = {localContext.startActivity(Intent(localContext, NewPlantActivity::class.java))}) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
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