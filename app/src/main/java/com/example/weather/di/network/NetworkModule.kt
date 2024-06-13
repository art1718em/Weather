package com.example.weather.di.network

import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient{
        return HttpClient(Android){
            install(JsonFeature){
                serializer = KotlinxSerializer()
            }
        }
    }
}