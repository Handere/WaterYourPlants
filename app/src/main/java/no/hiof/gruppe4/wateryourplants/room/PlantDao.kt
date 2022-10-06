package no.hiof.gruppe4.wateryourplants.room

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface PlantDao {

    @Insert
    fun insertPlant()
}