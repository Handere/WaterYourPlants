package no.hiof.gruppe4.wateryourplants.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Insert
    fun insertPlant(plant: Plant)

    @Insert
    fun insertPlantRoom(plantRoom: PlantRoom)

    @Transaction
    @Query("SELECT * FROM plantRoom_table")
    fun getPlantRoomWithPlants(): Flow<List<PlantRoomWithPlants>>

    @Query("SELECT * FROM plant_table")
    fun getAllPlants(): Flow<List<Plant>>

    @Query("SELECT * FROM plantRoom_table")
    fun getAllPlantRooms(): Flow<List<PlantRoom>>

    //TODO: add queries as needed
}