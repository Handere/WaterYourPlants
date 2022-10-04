package no.hiof.gruppe4.wateryourplants.model
import no.hiof.gruppe4.wateryourplants.model.Plant

data class PlantRoom(
    val id: Long,
    val name: String,
    val listOfPlants: List<Plant>
)

val plantRoom = listOf(
    PlantRoom (
        id = 0,
        name = "stue",
        listOfPlants = plants
    ),

    PlantRoom (
        id = 1,
        name = "kjøkken",
        listOfPlants = plants
    )
)

