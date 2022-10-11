package no.hiof.gruppe4.wateryourplants.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Insert
    fun insertPlant(plant: PlantEntity)

    @Query("SELECT * FROM plant_table")
    fun getAllPlants(): Flow<List<PlantEntity>>

    //TODO: add queries as needed
}