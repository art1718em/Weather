package com.example.weather.di.citiesScreen

import androidx.navigation.NavController
import com.example.weather.di.network.NetworkModule
import com.example.weather.ui.citiesScreen.CitiesPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface CitiesScreenComponent {

    fun citiesPresenter(): CitiesPresenter

    @Component.Factory
    interface Factory{

        fun create(@BindsInstance navController: NavController) : CitiesScreenComponent
    }
}