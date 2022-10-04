package no.hiof.gruppe4.wateryourplants.model

import androidx.compose.ui.graphics.painter.Painter

data class Plant(
    val id: Long,
    val species: String,
    val speciesLatin: String,
    val classification: String,
    val age: Int,
    val painter: Painter? = null,
    val wateringInterval: Int, // Indicate how many days between
    val nutritionInterval: Int, // Indicate how many days between
    val sunRequirement: String,
    val note: String
)

/*DateTimePatterngenerator

Valg blandt uke dager med en uke intervall

date picker = https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
*/

object PlantRepo {
    fun getPlant(plantId: Long): Plant = plants.find{it.id == plantId}!!
}

val plants = listOf(
    Plant(
        id = 0,
        species = "Monstera",
        speciesLatin = "Monstera deliciosa",
        classification = "Blomsterplanter",
        age = 2,
        wateringInterval = 10,
        nutritionInterval = 10,
        sunRequirement = "lite",
        note = ""
    ),

    Plant(
        id = 1,
        species = "Bjørkefiken",
        speciesLatin = "Ficus benjamina 'Danielle'",
        classification = "Blomsterplanter",
        age = 2,
        wateringInterval = 5,
        nutritionInterval = 5,
        sunRequirement = "halvskygge",
        note = ""
    )

)
