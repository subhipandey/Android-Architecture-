package com.subhipandey.android.weatherapp

interface DependencyInjector {
    fun weatherRepository() : WeatherRepository
}