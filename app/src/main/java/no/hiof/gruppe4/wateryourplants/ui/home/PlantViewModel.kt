package no.hiof.gruppe4.wateryourplants.home

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import no.hiof.gruppe4.wateryourplants.data.*

class PlantViewModel(private val repository: PlantRepository, plantRoomId: Int) : ViewModel() {

    //var allPlants: LiveData<List<Plant>> = repository.getPlants().asLiveData()

    fun insertPlant(
        roomId: Int,
        speciesName: String,
        speciesLatinName: String,
        plantClassification: String,
        photoUrl: Int,
        wateringInterval: Int,
        nutritionInterval: Int,
        wateringAndNutritionDay: String,
        sunRequirement: String,
        note: String
    ) = viewModelScope.launch {
       repository.insertPlant(Plant(roomId, speciesName, speciesLatinName, plantClassification, photoUrl, wateringInterval, nutritionInterval, wateringAndNutritionDay, sunRequirement, note))
    }

    var plantRoomList: LiveData<List<PlantRoom>> = repository.getPlantRooms().asLiveData()

    var plantRoomPlantList: LiveData<List<Plant>> = repository.getPlantRoomPlants(plantRoomId).asLiveData()

    var currentPlantRoom: LiveData<PlantRoom> = repository.getPlantRoom(plantRoomId).asLiveData()
}

// TODO: Error handling if plantRoomId = 0 = no argument given
class PlantViewModelFactory(val repository: PlantRepository, val plantRoomId: Int = 0) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlantViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PlantViewModel(repository, plantRoomId) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}