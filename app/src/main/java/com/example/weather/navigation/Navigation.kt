package com.example.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weather.di.citiesScreen.DaggerCitiesScreenComponent
import com.example.weather.di.weatherComponent.DaggerWeatherScreenComponent


import com.example.weather.ui.citiesScreen.composables.CitiesScreen
import com.example.weather.ui.weatherScreen.composables.WeatherScreen


@Composable
fun Navigation(){

    val navController = rememberNavController()

    val citiesScreenComponent = remember  {
        DaggerCitiesScreenComponent.factory().create(navController)
    }

    val weatherScreenComponent = remember  {
        DaggerWeatherScreenComponent.factory().create(navController)
    }


    NavHost(
        navController = navController,
        startDestination = Screen.CitiesScreen.route,
    ){
        composable(
            route = Screen.CitiesScreen.route,
        ){

            val presenter = remember  {
                citiesScreenComponent.citiesPresenter()
            }
            CitiesScreen(
                presenter = presenter,
            )
        }
        composable(
            route = "${Screen.WeatherScreen.route}/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) {

            val weatherPresenter = remember  {
                weatherScreenComponent.weatherPresenter()
            }

            WeatherScreen(presenter = weatherPresenter)
        }
    }
}