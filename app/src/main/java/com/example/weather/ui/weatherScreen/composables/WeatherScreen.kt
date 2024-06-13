package com.example.weather.ui.weatherScreen.composables

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.ui.design.ErrorScreen
import com.example.weather.ui.design.ProgressBar
import com.example.weather.ui.design.RegularButton
import com.example.weather.ui.weatherScreen.WeatherPresenter
import com.example.weather.ui.weatherScreen.state.LastUpdateTimeUiModel
import com.example.weather.ui.weatherScreen.state.MainInfoUiModel
import com.example.weather.ui.weatherScreen.state.WeatherScreenUiEffects
import com.example.weather.ui.weatherScreen.state.WeatherScreenUiState
import kotlinx.coroutines.flow.SharedFlow


@Composable
fun WeatherScreen(
    presenter: WeatherPresenter,
){
    

    val weatherUiState = presenter.weatherScreenUiState.collectAsState().value
    
    ShowUiEffectIfNeeded(weatherScreenUiEffects = presenter.effectFlow)

    when(weatherUiState){
        is WeatherScreenUiState.Loading -> ProgressBar()
        is WeatherScreenUiState.Error -> {
            Log.d("mytag", weatherUiState.message)
            ErrorScreen(presenter::loadWeather)
        }
        is WeatherScreenUiState.Success -> WeatherInfo(
            cityName = weatherUiState.cityName,
            mainInfoUiModel = weatherUiState.mainInfoUiModel,
            lastUpdateTimeUiModel = weatherUiState.lastUpdateTimeUiModel,
            onClick = presenter::loadWeather,
        )
    }

}

@Composable
fun WeatherInfo(
    cityName: String,
    mainInfoUiModel: MainInfoUiModel,
    lastUpdateTimeUiModel: LastUpdateTimeUiModel,
    onClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 40.dp,
                bottom = 36.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = buildString {
                        append(mainInfoUiModel.temp)
                        append(stringResource(id = R.string.degree_prefix))
                    },
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontSize = 57.sp,
                    lineHeight = 64.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = cityName,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontSize = 32.sp,
                    lineHeight = 40.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                val updatedText = "${stringResource(id = R.string.was_updated)} " +
                        "${lastUpdateTimeUiModel.hours}:${lastUpdateTimeUiModel.minutes}"
                Text(
                    text = updatedText,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
        RegularButton(
            text = stringResource(id = R.string.update),
            onClick = onClick,
        )
    }
}

@Composable
private fun ShowUiEffectIfNeeded(
    weatherScreenUiEffects: SharedFlow<WeatherScreenUiEffects>,
){
    
    val context = LocalContext.current
    
    val errorMessage = stringResource(id = R.string.update_error)
    
    LaunchedEffect(key1 = Unit) {
        weatherScreenUiEffects.collect{weatherScreenUiEffect -> 
            when(weatherScreenUiEffect){
                is WeatherScreenUiEffects.ShowSomethingWentWrongMessage -> {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
}