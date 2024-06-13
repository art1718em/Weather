package com.example.weather.app

import android.app.Application
import com.example.weather.di.app.AppComponent
import com.example.weather.di.app.DaggerAppComponent

class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

}