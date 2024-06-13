package com.example.weather.di.weatherComponent

import androidx.navigation.NavController
import com.example.weather.di.network.NetworkModule
import com.example.weather.ui.weatherScreen.WeatherPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface WeatherScreenComponent {

    fun weatherPresenter(): WeatherPresenter

    @Component.Factory
    interface Factory{

        fun create(@BindsInstance navController: NavController) : WeatherScreenComponent
    }
}