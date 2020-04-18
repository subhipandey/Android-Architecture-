package com.subhipandey.android.weatherapp

class DependencyInjectorImpl : DependencyInjector {
    override fun weatherRepository(): WeatherRepository{
        return WeatherRepositoryImpl()
    }
}