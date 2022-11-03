package no.hiof.gruppe4.wateryourplants.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import no.hiof.gruppe4.wateryourplants.data.*
import java.sql.Date
import java.time.LocalDate

class PlantViewModel(private val repository: PlantRepository, plantRoomId: Int, plantId: Int) : ViewModel() {

    //var allPlants: LiveData<List<Plant>> = repository.getPlants().asLiveData()

    // TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
    @RequiresApi(Build.VERSION_CODES.O)
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
        val currentDate = Date.valueOf(LocalDate.now().toString())
        val nextWateringDay = Date.valueOf(LocalDate.now().plusDays(wateringInterval.toLong()).toString())
       repository.insertPlant(Plant(roomId, speciesName, speciesLatinName, plantClassification, photoUrl, wateringInterval, nutritionInterval, wateringAndNutritionDay, sunRequirement, note, currentDate, nextWateringDay))
    }

    fun insertPlantRoom(plantRoomName: String) = viewModelScope.launch {repository.insertPlantRoom(PlantRoom(plantRoomName))}

    fun deletePlantRoomAndPlants(plantRoom: PlantRoom, plantRoomPlants: List<Plant>) = viewModelScope.launch { repository.deletePlantRoomAndPlants(plantRoom, plantRoomPlants) }

    var plantDetails: LiveData<Plant> = repository.getPlant(plantRoomId, plantId).asLiveData()

    var plantRoomList: LiveData<List<PlantRoom>> = repository.getPlantRooms().asLiveData()

    var plantRoomPlantList: LiveData<List<Plant>> = repository.getPlantRoomPlants(plantRoomId).asLiveData()

    var currentPlantRoom: LiveData<PlantRoom> = repository.getPlantRoom(plantRoomId).asLiveData()
}

// TODO: Error handling if plantRoomId = 0 = no argument given
class PlantViewModelFactory(val repository: PlantRepository, val plantRoomId: Int = 0, val plantId: Int = 0) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlantViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PlantViewModel(repository, plantRoomId, plantId) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}