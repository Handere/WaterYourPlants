package no.hiof.gruppe4.wateryourplants.data

class PlantRepository(private val plantDao: PlantDao) {

    //fun getPlantRoomWithPlants() = plantDao.getPlantRoomWithPlants()

    fun getPlants() = plantDao.getAllPlants()

   /* fun insertPlant() = plantDao.insertPlant()*/

    /*fun getPlant() = plantDao.*/

}