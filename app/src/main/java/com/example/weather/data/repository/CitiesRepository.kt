package com.example.weather.data.repository

import com.example.weather.data.network.PostService
import com.example.weather.domain.mapper.CitiesJsonMapper
import com.example.weather.domain.model.City
import com.example.weather.utils.EMPTY_LIST_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesRepository @Inject constructor(
    private val service: PostService,
    private val mapper: CitiesJsonMapper,
) {

    suspend fun getCities() : Result<List<City>> {
        return withContext(Dispatchers.IO) {
            try {
                val citiesJson = service.getCities()
                val listCities = mapper.map(citiesJson)
                if (listCities.isNotEmpty())
                    Result.success(listCities)
                else
                    Result.failure(Exception(EMPTY_LIST_MESSAGE))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}