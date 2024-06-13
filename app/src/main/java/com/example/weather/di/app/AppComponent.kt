package com.example.weather.di.app

import com.example.weather.di.network.NetworkModule
import com.example.weather.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
}