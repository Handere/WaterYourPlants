package no.hiof.gruppe4.wateryourplants.room

import android.app.Application
import androidx.lifecycle.*

class PlantViewModel(private val repository: PlantRepository) : ViewModel() {

    var allPlants: LiveData<List<PlantEntity>> = repository.getPlants().asLiveData()

    /*val searchResults: MutableLiveData<List<PlantEntity>>

    init {
        val plantDb = PlantRoomDatabase.getInstance()
        val plantDao = plantDb.plantDao()
        repository = PlantRepository(plantDao)

        allPlants = repository.allPlants
        searchResults = repository.searchResults
    }

    fun insertPlant(plantEntity: PlantEntity) {
        repository.insertPlant(plantEntity)
    }

    fun findProduct(name: String) {
        repository.findProduct(name)
    }

    fun deleteProduct(name: String) {
        repository.deleteProduct(name)
    }*/
}

//TODO: added plantViewFactory

class PlantViewModelFactory(val repository: PlantRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlantViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PlantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}