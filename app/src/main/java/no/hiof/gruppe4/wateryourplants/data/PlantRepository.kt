package no.hiof.gruppe4.wateryourplants.data

class PlantRepository(private val plantDao: PlantDao) {

    fun getPlants() = plantDao.getAllPlants()

    fun getPlantRoom(plantRoomId: Int) = plantDao.getPlantRoom(plantRoomId)

    fun getPlantRooms() = plantDao.getAllPlantRooms()

    fun getPlantRoomPlants(plantRoomId: Int) = plantDao.getPlantRoomPlants(plantRoomId)

}