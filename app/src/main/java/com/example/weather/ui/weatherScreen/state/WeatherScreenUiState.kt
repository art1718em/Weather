package com.example.weather.ui.weatherScreen.state

sealed interface WeatherScreenUiState {
    data object Loading : WeatherScreenUiState
    data class Error(val message: String) : WeatherScreenUiState
    data class Success(
        val cityName: String,
        val mainInfoUiModel: MainInfoUiModel,
        val lastUpdateTimeUiModel: LastUpdateTimeUiModel,
    ) : WeatherScreenUiState
}