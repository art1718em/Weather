package com.example.weather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.weather.app.App
import com.example.weather.navigation.Navigation
import com.example.weather.ui.design.theme.WeatherTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)

        setContent {
            WeatherTheme {
                Navigation()
            }
        }
    }
}

