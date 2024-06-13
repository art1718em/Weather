package com.example.weather.ui.citiesScreen

import androidx.compose.runtime.Stable
import javax.inject.Inject
import javax.inject.Singleton

@Stable
@Singleton
class CitiesPresenter @Inject constructor(
    private val viewModel: CitiesScreenViewModel,
) {

    val citiesScreenUiState = viewModel.citiesScreenUiState

    fun navigateToWeather(cityName: String){
        viewModel.navigateToWeather(cityName)
    }

    fun loadCities(){
        viewModel.loadCities()
    }
}