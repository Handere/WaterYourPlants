package no.hiof.gruppe4.wateryourplants.ui.home

import android.app.Application
import android.app.appsearch.AppSearchResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.gruppe4.wateryourplants.Routes
import no.hiof.gruppe4.wateryourplants.WaterYourPlantsApplication
import no.hiof.gruppe4.wateryourplants.room.*
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards


@Composable
fun RoomScreen(

    onNavigationToCreatePlant: (String, String) -> Unit,
    userName: String?,
    roomName: String?,
    modifier: Modifier = Modifier) {

    val viewModel: PlantViewModel = viewModel(factory = PlantViewModelFactory((LocalContext.current.applicationContext as WaterYourPlantsApplication).repository))
    val allPlants by viewModel.allPlants.observeAsState(listOf())
    //val searchResults by viewModel.searchResults.observeAsState(listOf())

    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigationToCreatePlant(userName.toString(),
                roomName.toString()
            ) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
        ) {padding ->
        Column(modifier = modifier.padding(padding)) {
            roomName?.let { it1 -> Text(text = it1.uppercase(), fontSize = 30.sp) }
            Spacer(modifier = modifier.height(5.dp))
            PlantCards(allPlants)
        }
    }
}