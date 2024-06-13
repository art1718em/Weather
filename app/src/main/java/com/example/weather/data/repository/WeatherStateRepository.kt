package com.example.weather.data.repository

import com.example.weather.domain.model.Weather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherStateRepository @Inject constructor() {

    private val cityWeather = mutableMapOf<String, Weather>()

    fun addCityWeather(
        cityName: String,
        weather: Weather,
    ){
        cityWeather[cityName] = weather
    }

    fun getCityWeather(cityName: String): Weather?{
        return if (isRequireUpdate(cityName)) {
            null
        }else{
            cityWeather[cityName]
        }
    }

    private fun isRequireUpdate(cityName: String): Boolean {
        return cityWeather[cityName]?.let {
            it.timeLastUpdate + AVAILABLE_TIME < System.currentTimeMillis()
        } ?: false
    }

    companion object{
        private const val AVAILABLE_TIME = 15 * 60 * 1000
    }
}