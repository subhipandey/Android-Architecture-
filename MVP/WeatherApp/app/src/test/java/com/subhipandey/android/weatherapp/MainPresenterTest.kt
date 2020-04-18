

package com.subhipandey.android.weatherapp

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    private lateinit var mockMainActivity: MainContract.View

    private val dependencyInjector: DependencyInjector = StubDependencyInjector()

    private var presenter: MainPresenter? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(mockMainActivity, dependencyInjector)
    }

    @After
    fun tearDown() {
        presenter?.onDestroy()
    }

    @Test
    fun testOnViewCreatedFlow() {
        presenter?.onViewCreated()
        verify(mockMainActivity).displayWeatherState(WeatherState.RAIN)
    }
}

class StubDependencyInjector : DependencyInjector {
    override fun weatherRepository(): WeatherRepository {
        return StubWeatherRepository()
    }
}
class StubWeatherRepository : WeatherRepository {
    override fun loadWeather(): Weather {
        var weather = Weather("xxx")
         var rain = Rain()
         rain.amount = 10
        weather.rain = rain
        return weather
    }
}