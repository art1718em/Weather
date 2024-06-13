package com.example.weather.data.repository

import com.example.weather.data.network.PostService
import com.example.weather.domain.mapper.WeatherJsonMapper
import com.example.weather.domain.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val service: PostService,
    private val mapper: WeatherJsonMapper,
) {

    suspend fun getWeather(cityName: String): Result<Weather>{
        return withContext(Dispatchers.IO){
            try {
                val weatherJson = service.getWeather(cityName)
                Result.success(mapper.map(weatherJson))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }
}