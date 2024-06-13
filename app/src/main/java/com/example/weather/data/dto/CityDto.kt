package com.example.weather.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(

    @SerialName("id")
    val id: String = "",

    @SerialName("city")
    val name: String = "",

    @SerialName("latitude")
    val latitude: String = "",

    @SerialName("longitude")
    val longitude: String = "",

)
