package no.hiof.gruppe4.wateryourplants.data

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.*

class PlantViewModel(private val repository: PlantRepository, plantRoomId: Int) : ViewModel() {

    //var allPlants: LiveData<List<Plant>> = repository.getPlants().asLiveData()

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