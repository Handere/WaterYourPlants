package no.hiof.gruppe4.wateryourplants.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class WeatherData(
    var temperature: Double,
    var symbolForForecast: String
)
{






}

