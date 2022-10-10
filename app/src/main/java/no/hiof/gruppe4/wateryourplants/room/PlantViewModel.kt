package no.hiof.gruppe4.wateryourplants.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlantViewModel(application: Application) : ViewModel() {

    val allPlants: LiveData<List<PlantEntity>>
    private val repository: PlantRepository
    val searchResults: MutableLiveData<List<PlantEntity>>

    init {
        val plantDb = PlantRoomDatabase.getInstance(application)
        val plantDao = plantDb.plantDao()
        repository = PlantRepository(plantDao)

        allPlants = repository.allPlants
        searchResults = repository.searchResults
    }

    fun insertPlant(plantEntity: PlantEntity) {
        repository.insertPlant(plantEntity)
    }
/*
    fun findProduct(name: String) {
        repository.findProduct(name)
    }

    fun deleteProduct(name: String) {
        repository.deleteProduct(name)
    }*/
}