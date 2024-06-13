package com.example.weather.ui.weatherScreen

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.weather.domain.interactor.WeatherInteractor
import com.example.weather.domain.model.MainInfo
import com.example.weather.navigation.Screen
import com.example.weather.ui.weatherScreen.state.LastUpdateTimeUiModel
import com.example.weather.ui.weatherScreen.state.MainInfoUiModel
import com.example.weather.ui.weatherScreen.state.WeatherScreenUiEffects
import com.example.weather.ui.weatherScreen.state.WeatherScreenUiState
import com.example.weather.utils.UNKNOWN_MESSAGE
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherScreenViewModel @Inject constructor(
    private val weatherInteractor: WeatherInteractor,
    navController: NavController,
) : ViewModel() {

    private val _weatherScreenUiState = MutableStateFlow<WeatherScreenUiState>(WeatherScreenUiState.Loading)
    val weatherScreenUiState = _weatherScreenUiState.asStateFlow()

    private var previousWeatherScreenUiState: WeatherScreenUiState? = null

    private val _effectFlow = MutableSharedFlow<WeatherScreenUiEffects>()
    val effectFlow = _effectFlow.asSharedFlow()

    private val cityName: String = navController
        .getBackStackEntry("${Screen.WeatherScreen.route}/{city}")
        .arguments
        ?.getString("city") ?: ""


    init{
        loadWeather()
    }

    fun loadWeather(forceUpdate: Boolean = false){
        if (weatherScreenUiState.value is WeatherScreenUiState.Success) {
            previousWeatherScreenUiState = weatherScreenUiState.value
        }
        _weatherScreenUiState.value = WeatherScreenUiState.Loading
        viewModelScope.launch {
            val result = weatherInteractor.getWeather(
                cityName = cityName,
                forceUpdate = forceUpdate,
            )
            if (result.isSuccess){
                result.getOrThrow().apply {
                    _weatherScreenUiState.value = WeatherScreenUiState.Success(
                        cityName = cityName,
                        mainInfoUiModel = mainInfo.toMainInfoUiModel(),
                        lastUpdateTimeUiModel = timeLastUpdate.toLastTimeUiModel(),
                    )
                }
            }else{
                val weatherUiState = previousWeatherScreenUiState
                if (weatherUiState != null){
                    _weatherScreenUiState.value = weatherUiState
                    _effectFlow.emit(WeatherScreenUiEffects.ShowSomethingWentWrongMessage)
                }else {
                    _weatherScreenUiState.value = WeatherScreenUiState.Error(
                        message = result.exceptionOrNull()?.message ?: UNKNOWN_MESSAGE
                    )
                }
            }
        }
    }
}

private fun MainInfo.toMainInfoUiModel(): MainInfoUiModel{
    return MainInfoUiModel(
        temp = Math.round(temp).toInt(),
    )
}

private fun Long.toLastTimeUiModel(): LastUpdateTimeUiModel{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return LastUpdateTimeUiModel(
        hours = calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0'),
        minutes = calendar.get(Calendar.MINUTE).toString().padStart(2, '0'),
    )
}