package com.example.weather.ui.weatherScreen.state

data class MainInfoUiModel(
    val temp: Int,
)

data class LastUpdateTimeUiModel(
    val hours: String,
    val minutes: String,
)