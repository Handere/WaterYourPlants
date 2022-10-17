package no.hiof.gruppe4.wateryourplants.data

import androidx.room.Embedded
import androidx.room.Relation


data class PlantRoomWithPlants(
    @Embedded val plantRoom: PlantRoom,
    @Relation(
        parentColumn = "plantRoomId",
        entityColumn = "roomId"
    )
    val plantList: List<Plant>
)