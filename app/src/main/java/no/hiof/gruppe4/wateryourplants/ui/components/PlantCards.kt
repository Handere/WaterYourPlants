package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import no.hiof.gruppe4.wateryourplants.model.Plant
import no.hiof.gruppe4.wateryourplants.model.plants
import no.hiof.gruppe4.wateryourplants.ui.home.HomeActivity

@Composable
fun PlantCards() {
    
    LazyColumn {
        items(plants) { // TODO: Change to list from view model
            PlantCard(
                contentDescription = it.species,
                plantName = it.species)
        }
    }
}