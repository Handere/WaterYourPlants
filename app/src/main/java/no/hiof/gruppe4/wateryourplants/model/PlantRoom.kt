package no.hiof.gruppe4.wateryourplants.model

data class PlantRoom(
    val id: Long,
        val name: String,
    val plants: List<Plant>
)


