package com.example.weather.domain.mapper

import com.example.weather.data.dto.CityDto
import com.example.weather.domain.model.City
import javax.inject.Inject

class CityMapper @Inject constructor() {

    fun map(cityDto: CityDto) : City {
        return City(
            name = cityDto.name,
            latitude = cityDto.latitude.toDouble(),
            longitude = cityDto.longitude.toDouble(),
        )
    }
}