package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import no.hiof.gruppe4.wateryourplants.model.plants

@Composable
fun PlantCards() {
    
    LazyColumn() {
        items(plants) { // TODO: Change to list from view model
            PlantCard(
                contentDescription = it.species,
                species = it.species,
                speciesLatin = it.speciesLatin)
        }
    }
}