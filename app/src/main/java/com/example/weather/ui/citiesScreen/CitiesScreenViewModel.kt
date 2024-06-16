package com.example.weather.ui.citiesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.weather.domain.interactor.CitiesInteractor
import com.example.weather.domain.model.City
import com.example.weather.navigation.Screen
import com.example.weather.ui.citiesScreen.state.CitiesScreenUiState
import com.example.weather.ui.citiesScreen.state.CityUiModel
import com.example.weather.utils.UNKNOWN_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesScreenViewModel @Inject constructor(
    private val navController: NavController,
    private val citiesInteractor: CitiesInteractor,
) : ViewModel() {

    private val _citiesScreenUiState = MutableStateFlow<CitiesScreenUiState>(CitiesScreenUiState.Loading)
    val citiesScreenUiState = _citiesScreenUiState.asStateFlow()

    init {
        loadCities()
    }

    fun navigateToWeather(cityName: String){
        navController.navigate("${Screen.WeatherScreen.route}/${cityName}")
    }

    fun loadCities(){
        _citiesScreenUiState.value = CitiesScreenUiState.Loading
        viewModelScope.launch {
            val result = citiesInteractor.getCities(forceUpdate = true)
            _citiesScreenUiState.value = if (result.isSuccess){
                val cities = result.getOrThrow().map { it.toCityUiModel() }
                CitiesScreenUiState.Success(
                    groupedCities = cities.groupBy { it.name[0] },
                    cities = cities,
                )
            } else{
                CitiesScreenUiState.Error(
                    result.exceptionOrNull()?.message ?: UNKNOWN_MESSAGE
                )
            }
        }
    }
}

private fun City.toCityUiModel() : CityUiModel{
    return CityUiModel(
        name = name,
    )
}