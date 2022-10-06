package no.hiof.gruppe4.wateryourplants.room

import android.icu.util.DateInterval
import android.service.autofill.FieldClassification
import androidx.compose.ui.graphics.painter.Painter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
class PlantEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "plantId")
    var plantId: Int = 0

    var speciesName: String = ""
    var speciesLatinName: String = ""
    var plantClassification: String = ""
    var plantAge: Int = 0
    var painter: Painter? = null
    var wateringInterval: Int  = 0// Indicate how many days between
    var nutritionInterval: Int = 0// Indicate how many days between
    var sunRequirement: String = ""
    var note: String = ""

    constructor() {}

    constructor(
        speciesName: String,
        speciesLatinName: String,
        plantClassification: String,
        plantAge: Int,
        painter: Painter,
        wateringInterval: Int,
        nutritionInterval: Int,
        sunRequirement: String,
        note: String) {

        this.speciesName = speciesName
        this.speciesLatinName = speciesLatinName
        this.plantClassification = plantClassification
        this.plantAge = plantAge
        this.painter = painter
        this.wateringInterval = wateringInterval
        this.nutritionInterval = nutritionInterval
        this.sunRequirement = sunRequirement
        this.note = note
    }


}