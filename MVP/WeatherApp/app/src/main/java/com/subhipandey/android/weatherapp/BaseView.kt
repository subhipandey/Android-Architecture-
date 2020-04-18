package com.subhipandey.android.weatherapp

interface BaseView<T> {
    fun setPresenter(presenter : T)
}