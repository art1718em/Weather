package com.example.weather.domain.interactor

import com.example.weather.data.repository.WeatherRepository
import com.example.weather.data.repository.WeatherStateRepository
import com.example.weather.domain.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherInteractor @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val weatherStateRepository: WeatherStateRepository,
){

    suspend fun getWeather(cityName: String, forceUpdate: Boolean): Result<Weather>{
       return withContext(Dispatchers.IO) {
           val lastWeather = weatherStateRepository.getCityWeather(cityName)
           if (forceUpdate || lastWeather == null){
               val result = weatherRepository.getWeather(cityName)
               if (result.isSuccess) {
                   weatherStateRepository.addCityWeather(cityName, result.getOrThrow())
               }
               result
           }else{
               Result.success(lastWeather)
           }
       }
    }
}