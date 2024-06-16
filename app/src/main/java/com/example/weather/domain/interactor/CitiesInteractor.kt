package com.example.weather.domain.interactor

import com.example.weather.data.repository.CitiesRepository
import com.example.weather.data.repository.CitiesStateRepository
import com.example.weather.domain.model.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CitiesInteractor @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val citiesStateRepository: CitiesStateRepository,
) {

    suspend fun getCities(forceUpdate: Boolean): Result<List<City>> {
        return withContext(Dispatchers.IO){
            val lastCities = citiesStateRepository.getCities()
            if (forceUpdate || lastCities == null){
                val result = citiesRepository.getCities()
                if (result.isSuccess){
                    citiesStateRepository.setCities(result.getOrThrow())
                }
                result
            } else {
                Result.success(lastCities)
            }
        }
    }
}