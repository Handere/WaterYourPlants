package no.hiof.gruppe4.wateryourplants.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import no.hiof.gruppe4.wateryourplants.data.Plant

// TODO: Can be removed

// TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
@RequiresApi(Build.VERSION_CODES.O)
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

