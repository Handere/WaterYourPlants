package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import no.hiof.gruppe4.wateryourplants.model.plants
import no.hiof.gruppe4.wateryourplants.room.PlantEntity

@Composable
fun PlantCards(plantList: List<PlantEntity>) {
    
    LazyColumn() {
        items(plantList) { // TODO: Change to list from view model
            PlantCard(
                contentDescription = it.speciesName,
                species = it.speciesName,
                speciesLatin = it.speciesLatinName)
        }
    }
}

