package no.hiof.gruppe4.wateryourplants.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import java.sql.Date

@Dao
interface PlantDao {


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlantRoom(plantRoom: PlantRoom)

    @Delete
    suspend fun deletePlantRoomAndPlants(plantRoom: PlantRoom, plantRoomPlants: List<Plant>)

    @Transaction
    @Query("SELECT * FROM plantRoom_table")
    fun getPlantRoomWithPlants(): Flow<List<PlantRoomWithPlants>>

    @Query("SELECT * FROM plantRoom_table WHERE plantRoomId = :plantRoomId")
    fun getPlantRoom(plantRoomId: Int): Flow<PlantRoom>

    @Query("SELECT * FROM plant_table WHERE roomId = :plantRoomId and plantId = :plantId")
    fun getPlant(plantRoomId: Int, plantId: Int): Flow<Plant>

    @Query("SELECT * FROM plant_table")
    fun getAllPlants(): Flow<List<Plant>>

    @Query("SELECT * FROM plant_table WHERE roomId = :plantRoomId")
    fun getPlantRoomPlants(plantRoomId: Int): Flow<List<Plant>>

    @Query("SELECT * FROM plantRoom_table")
    fun getAllPlantRooms(): Flow<List<PlantRoom>>

    @Query("UPDATE plant_table SET lastWateringDate = :lastWateringDate, nextWateringDate = :nextWateringDate WHERE plantId = :id")
    suspend fun updateWateringDate(lastWateringDate: Date, nextWateringDate: Date, id: Int)

    //TODO: add queries as needed
}