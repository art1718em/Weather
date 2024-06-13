package com.example.weather.domain.model

data class Weather(
    val mainInfo: MainInfo,
    val timeLastUpdate: Long = System.currentTimeMillis()
)

data class MainInfo(
    val temp: Double,
)