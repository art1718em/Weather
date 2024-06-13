package com.example.weather.domain.mapper

import com.example.weather.data.dto.CityDto
import com.example.weather.data.network.response.CitiesListJson
import com.example.weather.domain.model.City
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CitiesJsonMapper @Inject constructor(
    private val mapper: CityMapper,
) {

    fun map(citiesJson: CitiesListJson): List<City>{
        val citiesDto: List<CityDto> = Json.decodeFromString(citiesJson.citiesList)
        return citiesDto
            .filter { it.name.isNotEmpty() }
            .map(mapper::map)
            .sortedBy { it.name }
    }

}