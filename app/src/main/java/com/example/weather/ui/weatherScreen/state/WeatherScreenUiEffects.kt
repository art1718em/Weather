package com.example.weather.ui.weatherScreen.state

sealed interface WeatherScreenUiEffects {
    data object ShowSomethingWentWrongMessage : WeatherScreenUiEffects
}