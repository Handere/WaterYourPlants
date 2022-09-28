package no.hiof.gruppe4.wateryourplants.model

data class Room(
    val id: Long,
        val name: String,
    val plants: List<Plant>
)
