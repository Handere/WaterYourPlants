package no.hiof.gruppe4.wateryourplants.data

import androidx.annotation.WorkerThread

class PlantRepository(private val plantDao: PlantDao) {

    @WorkerThread
    suspend fun insertPlant(plant: Plant) = plantDao.insertPlant(plant)

    @WorkerThread
    suspend fun insertPlantRoom(plantRoom: PlantRoom) = plantDao.insertPlantRoom(plantRoom)

    @WorkerThread
    suspend fun deletePlantRoomAndPlants(plantRoom: PlantRoom, plantRoomPlants: List<Plant>) = plantDao.deletePlantRoomAndPlants(plantRoom, plantRoomPlants)

    fun getPlantRoom(plantRoomId: Int) = plantDao.getPlantRoom(plantRoomId)

    fun getPlantRooms() = plantDao.getAllPlantRooms()

    fun getPlantRoomPlants(plantRoomId: Int) = plantDao.getPlantRoomPlants(plantRoomId)

}