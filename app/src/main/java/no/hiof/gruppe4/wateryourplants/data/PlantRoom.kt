package no.hiof.gruppe4.wateryourplants.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plantRoom_table")
data class PlantRoom(
    var roomName: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "plantRoomId")
    var plantRoomId: Int = 0

}