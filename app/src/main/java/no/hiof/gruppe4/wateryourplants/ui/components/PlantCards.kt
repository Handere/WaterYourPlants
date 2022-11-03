package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import no.hiof.gruppe4.wateryourplants.data.Plant

@Composable
fun PlantCards(
    onNavigationToCreatePlant: (String, Int, Int) -> Unit,
    userName: String?,
    plantRoomId: Int,
    plantList: List<Plant>) {
    
    LazyColumn() {
        items(plantList) { // TODO: Change to list from view model
            PlantCard(
                onNavigationToPlantDetails = onNavigationToCreatePlant,
                userName = userName,
                plantRoomId = plantRoomId,
                plantId = it.plantId,
                contentDescription = it.speciesName,
                species = it.speciesName,
                speciesLatin = it.speciesLatinName,
                nextWateringDay = it.nextWateringDate)
        }
    }
}

