

package com.subhipandey.android.weatherapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView

// 1
class MainActivity : AppCompatActivity(), MainContract.View {
    internal lateinit var imageView: ImageView
    internal lateinit var button: Button

    // 2
    internal lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        button = findViewById(R.id.button)

        // 3
        setPresenter(MainPresenter(this, DependencyInjectorImpl()))
        presenter.onViewCreated()

        // 4
        button.setOnClickListener { presenter.onLoadWeatherTapped() }
    }

    // 5
    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    // 6
    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    // 7
    override fun displayWeatherState(weatherState: WeatherState) {
        val drawable = resources.getDrawable(weatherDrawableResId(weatherState),
                applicationContext.getTheme())
        this.imageView.setImageDrawable(drawable)
    }

    fun weatherDrawableResId(weatherState: WeatherState) : Int {
        return when (weatherState) {
            WeatherState.SUN -> R.drawable.ic_sun
            WeatherState.RAIN -> R.drawable.ic_umbrella
        }
    }
}