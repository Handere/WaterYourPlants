package no.hiof.gruppe4.wateryourplants.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "plant_table")
data class Plant(
    var roomId: Int,
    var speciesName: String,
    var speciesLatinName: String,
    var plantClassification: String,
    var purchasedDate: LocalDate,
    var photoUrl: Int,
    var wateringInterval: Int,
    var nutritionInterval: Int,
    var wateringAndNutritionDay: String,
    var sunRequirement: String,
    var note: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "plantId")
    var plantId: Int = 0

    //TODO: painter may not work with database, consider image handling(coil)

    }


