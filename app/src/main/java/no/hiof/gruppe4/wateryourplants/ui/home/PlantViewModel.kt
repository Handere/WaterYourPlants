package no.hiof.gruppe4.wateryourplants.home

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import no.hiof.gruppe4.wateryourplants.data.*
import no.hiof.gruppe4.wateryourplants.screen.LoadingState

class PlantViewModel(private val repository: PlantRepository, plantRoomId: Int) : ViewModel() {

    //var allPlants: LiveData<List<Plant>> = repository.getPlants().asLiveData()

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    // TODO: Try this?: https://www.composables.co/blog/firebase-auth

    // Code from: https://ericampire.com/firebase-auth-with-jetpack-compose
    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

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

    fun insertPlantRoom(plantRoomName: String) = viewModelScope.launch {repository.insertPlantRoom(PlantRoom(plantRoomName))}

    fun deletePlantRoomAndPlants(plantRoom: PlantRoom, plantRoomPlants: List<Plant>) = viewModelScope.launch { repository.deletePlantRoomAndPlants(plantRoom, plantRoomPlants) }

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