package no.hiof.gruppe4.wateryourplants.data

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.*

class PlantViewModel(private val repository: PlantRepository) : ViewModel() {

    var plantRoomWithPlants: LiveData<List<PlantRoomWithPlants>> = repository.getPlantRoomWithPlants().asLiveData()

    var allPlants: LiveData<List<Plant>> = repository.getPlants().asLiveData()

    //TODO: no longer livedata
    fun getPlantRoomPlants(plantRoom: String?): List<Plant>? {
        var plantList: List<Plant>? = null
        plantRoomWithPlants.value?.forEach {
            if (it.plantRoom.roomName.equals(plantRoom)){
                plantList =  it.plantList
            }
        }
        return plantList
    }
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