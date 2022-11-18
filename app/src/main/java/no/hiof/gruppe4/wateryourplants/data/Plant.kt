package no.hiof.gruppe4.wateryourplants.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.OffsetDateTime

@Entity(tableName = "plant_table")
data class Plant(
    var roomId: Int,
    var speciesName: String,
    var speciesLatinName: String,
    var plantClassification: String,
    var photoUrl: String,
    var wateringInterval: Int,
    var nutritionInterval: Int,
    var wateringAndNutritionDay: String,
    var sunRequirement: String,
    var note: String,
    var lastWateringDate: Date,
    var nextWateringDate: Date,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "plantId")
    var plantId: Int = 0

    //TODO: painter may not work with database, consider image handling(coil)

    }


