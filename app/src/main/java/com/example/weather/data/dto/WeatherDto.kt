package com.example.weather.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(

    @SerialName("main")
    val mainInfoDto: MainInfoDto
)

@Serializable
data class MainInfoDto(

    @SerialName("temp")
    val temp: Double
)