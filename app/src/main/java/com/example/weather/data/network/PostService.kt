package com.example.weather.data.network

import com.example.weather.data.network.response.CitiesListJson
import com.example.weather.data.network.response.WeatherJson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PostService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getCities() : CitiesListJson{
        return withContext(Dispatchers.IO){
            CitiesListJson(client.get(CITIES_URL))
        }
    }

    suspend fun getWeather(cityName: String) : WeatherJson{
        return withContext(Dispatchers.IO){
            WeatherJson(client.get(getWeatherUrl(cityName)))
        }
    }
}
