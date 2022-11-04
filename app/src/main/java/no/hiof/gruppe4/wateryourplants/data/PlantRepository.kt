package no.hiof.gruppe4.wateryourplants.data

import androidx.annotation.WorkerThread
import java.sql.Date

class PlantRepository(private val plantDao: PlantDao) {

    @WorkerThread
    suspend fun insertPlant(plant: Plant) = plantDao.insertPlant(plant)

    @WorkerThread
    suspend fun insertPlantRoom(plantRoom: PlantRoom) = plantDao.insertPlantRoom(plantRoom)

    @WorkerThread
    suspend fun deletePlantRoomAndPlants(plantRoom: PlantRoom, plantRoomPlants: List<Plant>) = plantDao.deletePlantRoomAndPlants(plantRoom, plantRoomPlants)

    @WorkerThread
    suspend fun deletePlant(plant: Plant) = plantDao.deletePlant(plant)

    fun getPlantRoom(plantRoomId: Int) = plantDao.getPlantRoom(plantRoomId)

    fun getPlantRooms() = plantDao.getAllPlantRooms()

    fun getPlantRoomPlants(plantRoomId: Int) = plantDao.getPlantRoomPlants(plantRoomId)

    fun getPlant(plantRoomId: Int, plantId: Int) = plantDao.getPlant(plantRoomId, plantId)

    @WorkerThread
    suspend fun updateWateringDate(lastWateringDate: Date, nextWateringDate: Date, id: Int) = plantDao.updateWateringDate(lastWateringDate, nextWateringDate, id)

}