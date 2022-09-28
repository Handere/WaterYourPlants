package no.hiof.gruppe4.wateryourplants.model

import android.icu.util.DateInterval

data class Plant(
    val id: Long,
    val species: String,
    val speciesLatin: String,
    val classification: String,
    val age: Int,
    val pictureURL: String,
    val wateringInterval: DateInterval,
    val nutritionInterval: DateInterval,
    val sunRequirement: String,
    val note: String
)
