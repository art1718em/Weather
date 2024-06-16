package com.example.weather.data.repository

import com.example.weather.domain.model.City
import javax.inject.Inject

class CitiesStateRepository @Inject constructor() {

    private var listCities = listOf<City>()

    fun setCities(cities: List<City>){
        listCities = cities
    }

    fun getCities(): List<City>?{
        return listCities.ifEmpty {
            null
        }
    }
}