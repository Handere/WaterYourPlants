package no.hiof.gruppe4.wateryourplants.room

import android.icu.util.DateInterval
import android.service.autofill.FieldClassification
import androidx.compose.ui.graphics.painter.Painter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "plant_table")
data class PlantEntity(
    var speciesName: String,
    var speciesLatinName: String,
    var plantClassification: String,
    // TODO: consider to change plantAge datatype
    var plantAge: Int,
    var photoUrl: Int,
    var wateringInterval: Int,
    var nutritionInterval: Int,
    var sunRequirement: String,
    var note: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "plantId")
    var plantId: Int = 0

    //TODO: painter may not work with database, consider image handling(coil)

   /* constructor(
        speciesName: String,
        speciesLatinName: String,
        plantClassification: String,
        plantAge: Int,
        /*painter: Painter,*/
        wateringInterval: Int,
        nutritionInterval: Int,
        sunRequirement: String,
        note: String) {

        this.speciesName = speciesName
        this.speciesLatinName = speciesLatinName
        this.plantClassification = plantClassification
        this.plantAge = plantAge
        /*this.painter = painter*/
        this.wateringInterval = wateringInterval
        this.nutritionInterval = nutritionInterval
        this.sunRequirement = sunRequirement
        this.note = note*/
    }


