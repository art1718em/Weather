package com.example.weather.ui.weatherScreen

import androidx.compose.runtime.Stable
import javax.inject.Inject

@Stable
class WeatherPresenter @Inject constructor(
    private val viewModel: WeatherScreenViewModel,
){

    val weatherScreenUiState = viewModel.weatherScreenUiState

    val effectFlow = viewModel.effectFlow

    fun loadWeather(){
        viewModel.loadWeather(forceUpdate = true)
    }
}