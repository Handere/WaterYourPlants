package no.hiof.gruppe4.wateryourplants.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {

    @Insert
    fun insertPlant(plant: PlantEntity)

    @Query("SELECT * FROM plants")
    fun getAllPlants(): LiveData<List<PlantEntity>>

    //TODO: add queries as needed
}