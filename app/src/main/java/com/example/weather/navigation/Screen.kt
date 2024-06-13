package com.example.weather.navigation

sealed class Screen(val route: String) {
    data object CitiesScreen : Screen("cities_screen")
    data object WeatherScreen : Screen("weather_screen")
}