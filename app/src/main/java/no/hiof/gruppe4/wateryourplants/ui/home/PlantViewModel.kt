package no.hiof.gruppe4.wateryourplants.home

import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import no.hiof.gruppe4.wateryourplants.data.*
import java.sql.Date
import java.time.LocalDate


class PlantViewModel(private val repository: PlantRepository, plantRoomId: Int, plantId: Int) : ViewModel() {

    //var allPlants: LiveData<List<Plant>> = repository.getPlants().asLiveData()

    // TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
    @RequiresApi(value = 26)
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

    fun deletePlant(plant: Plant) = viewModelScope.launch { repository.deletePlant(plant) }

    // TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
    @RequiresApi(value = 26)
    fun updateWateringDate(
        wateringInterval: Int,
        plantId: Int) = viewModelScope.launch {
        val currentDate = Date.valueOf(LocalDate.now().toString())
        val nextWateringDay = Date.valueOf(LocalDate.now().plusDays(wateringInterval.toLong()).toString())

        repository.updateWateringDate(currentDate, nextWateringDay, plantId)
    }

    fun insertPlantRoom(plantRoomName: String) = viewModelScope.launch {repository.insertPlantRoom(PlantRoom(plantRoomName))}

    fun deletePlantRoomAndPlants(plantRoom: PlantRoom, plantRoomPlants: List<Plant>) = viewModelScope.launch { repository.deletePlantRoomAndPlants(plantRoom, plantRoomPlants) }

    var currentPlant: LiveData<Plant> = repository.getPlant(plantRoomId, plantId).asLiveData()

    var plantRoomList: LiveData<List<PlantRoom>> = repository.getPlantRooms().asLiveData()

    var plantRoomPlantList: LiveData<List<Plant>> = repository.getPlantRoomPlants(plantRoomId).asLiveData()

    var currentPlantRoom: LiveData<PlantRoom> = repository.getPlantRoom(plantRoomId).asLiveData()

    fun getPlantRoomPlantList(plantRoomId: Int): LiveData<List<Plant>> {
        return repository.getPlantRoomPlants(plantRoomId).asLiveData()
    }

    // TODO: LocalDate.now() requires API lvl 26 or higher (current supported is 21)
    @RequiresApi(value = 26)
    fun numberOfNotifyingPlants(plantList: List<Plant>): Int {
        var notifications = 0
        plantList.forEach {
            if (it.nextWateringDate.compareTo(Date.valueOf(LocalDate.now().toString())) <= 0) {
                notifications++
            }
        }
        return notifications
    }
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