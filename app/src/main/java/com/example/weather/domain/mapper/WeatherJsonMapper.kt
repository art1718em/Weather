package com.example.weather.domain.mapper

import com.example.weather.data.dto.WeatherDto
import com.example.weather.data.network.response.WeatherJson
import com.example.weather.domain.model.Weather
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WeatherJsonMapper @Inject constructor(
    private val mapper: WeatherMapper,
) {

    fun map(weatherJson: WeatherJson): Weather{
        val json = Json { ignoreUnknownKeys = true }
        val weatherDto = json.decodeFromString<WeatherDto>(weatherJson.weather)
        return mapper.map(weatherDto)
    }
}