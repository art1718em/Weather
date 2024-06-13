package com.example.weather.domain.mapper

import com.example.weather.data.dto.WeatherDto
import com.example.weather.domain.model.MainInfo
import com.example.weather.domain.model.Weather
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

    fun map(weatherDto: WeatherDto): Weather{
        return Weather(
            mainInfo = MainInfo(
                temp = weatherDto.mainInfoDto.temp,
            )
        )
    }
}