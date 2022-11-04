package no.hiof.gruppe4.wateryourplants.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.hiof.gruppe4.wateryourplants.data.WeatherData

class WeatherViewModel : ViewModel(){
    private val weatherResponse = mutableStateListOf<WeatherData>()
    var errorMessage: String by mutableStateOf("")
    val weather: List<WeatherData>
        get() = weatherResponse

    fun getWeather() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                weatherResponse.clear()
                weatherResponse.addAll(apiService.getWeather())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}