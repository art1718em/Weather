package com.example.weather.ui.citiesScreen.state

sealed interface CitiesScreenUiState{
    data object Loading : CitiesScreenUiState
    data class Error(val message: String) : CitiesScreenUiState
    data class Success(
        val groupedCities: Map<Char, List<CityUiModel>>,
        val cities: List<CityUiModel>,
    ) : CitiesScreenUiState
}