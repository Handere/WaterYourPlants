package no.hiof.gruppe4.wateryourplants.model

data class User(
    val id: Long,
    val username: String,
    val password: String,
    val firstName: String,
    val surname: String,
    val profilePictureURL: String,
    val rooms: List<PlantRoom>
)
