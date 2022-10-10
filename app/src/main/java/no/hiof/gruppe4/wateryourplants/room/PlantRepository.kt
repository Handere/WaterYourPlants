package no.hiof.gruppe4.wateryourplants.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantRepository(private val plantDao: PlantDao) {

    val allPlants: LiveData<List<PlantEntity>> = plantDao.getAllPlants()

    val searchResults = MutableLiveData<List<PlantEntity>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertPlant(newPlant: PlantEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            plantDao.insertPlant(newPlant)
        }
    }
/*
    fun deleteProduct(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            productDao.deleteProduct(name)
        }
    }

    fun findProduct(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name: String): Deferred<List<Product>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async productDao.findProduct(name)
        }*/
}