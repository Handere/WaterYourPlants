package no.hiof.gruppe4.wateryourplants.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import no.hiof.gruppe4.wateryourplants.model.Plant
import no.hiof.gruppe4.wateryourplants.ui.home.HomeActivity

@Composable
fun PlantCards() {
    val description = "Test card description"
    val plantName = "Test plant name"

// Just for testing the UI
    LazyColumn {
        items(10) {
            PlantCard(
                contentDescription = description,
                plantName = "$plantName $it")
        }
    }
}