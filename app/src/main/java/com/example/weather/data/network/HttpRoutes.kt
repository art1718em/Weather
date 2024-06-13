package com.example.weather.data.network

const val CITIES_URL =
        "https://gist.githubusercontent.com/Stronger197/764f9886a1e8392ddcae2521437d5a3b/raw/65164ea1af958c75c81a7f0221bead610590448e/cities.json"

private const val WEATHER_URL =
        "https://api.openweathermap.org/data/2.5/weather?"

private const val API_KEY = "0934ae1c8876554b925bf4f264bb474d"

private const val UNITS = "metric"

fun getWeatherUrl(
        cityName: String,
): String {
        return "${WEATHER_URL}q=$cityName&appid=$API_KEY&units=$UNITS"
}