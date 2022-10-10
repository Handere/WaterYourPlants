package no.hiof.gruppe4.wateryourplants.ui.home

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.hiof.gruppe4.wateryourplants.Routes
import no.hiof.gruppe4.wateryourplants.room.PlantEntity
import no.hiof.gruppe4.wateryourplants.room.PlantViewModel
import no.hiof.gruppe4.wateryourplants.ui.components.PlantCards

/*//TODO: add viewModel
@Composable
fun ScreenSetup(viewModel: PlantViewModel) {

    val allPlants by viewModel.allPlants.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    RoomScreen(
        allPlants = allPlants,
        searchResults = searchResults,
        viewModel = viewModel
    )
}*/

@Composable
fun RoomScreen(
    /*allPlants: List<PlantEntity>,
    searchResults: List<PlantEntity>,
    viewModel: PlantViewModel,*/
    onNavigationToCreatePlant: (String, String) -> Unit,
    userName: String?,
    roomName: String?,
    modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { ScaffoldTopAppBar(userName) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigationToCreatePlant(userName.toString(),
                roomName.toString()
            ) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Column(modifier = modifier.padding(16.dp, 0.dp)) {
            roomName?.let { it1 -> Text(text = it1.uppercase(), fontSize = 30.sp) }
            Spacer(modifier = modifier.height(5.dp))
            PlantCards()
        }
    }
}