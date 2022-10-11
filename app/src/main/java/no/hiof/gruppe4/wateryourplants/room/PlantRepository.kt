package no.hiof.gruppe4.wateryourplants.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantRepository(private val plantDao: PlantDao) {

    fun getPlants() = plantDao.getAllPlants()

   /* fun insertPlant() = plantDao.insertPlant()*/

    /*fun getPlant() = plantDao.*/

}