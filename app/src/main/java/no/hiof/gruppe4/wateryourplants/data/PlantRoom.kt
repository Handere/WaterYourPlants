package no.hiof.gruppe4.wateryourplants.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plantRoom_table")
data class PlantRoom(
    var roomName: String
) {

    @PrimaryKey(autoGenerate = true)
    var plantRoomId: Int = 0

}