package com.subhipandey.android.weatherapp

interface MainContract {
    interface Presenter : BasePresenter{
        fun onViewCreated()
        fun onLoadWeatherTapped()
    }

    interface View: BaseView<Presenter>{
        fun displayWeatherState(weatherState: WeatherState)
    }
}