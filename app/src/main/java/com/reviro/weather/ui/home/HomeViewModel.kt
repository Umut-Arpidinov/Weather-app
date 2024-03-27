package com.reviro.weather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.reviro.common.base.BaseViewModel
import com.reviro.domain.interfaces.HomeRepository
import com.reviro.domain.model.Location
import com.reviro.domain.model.WeatherForecast
import com.reviro.domain.usecases.GetWeatherByLocationUseCase
import com.reviro.domain.usecases.GetWeatherByQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getWeatherByQueryUseCase: GetWeatherByQueryUseCase,
    private val homeRepository: HomeRepository
) : BaseViewModel() {


    enum class WeatherThemeState {
        SNOW, RAIN, CLEAR, CLOUD, UNDEFINED
    }


    private val _weatherThemeState: MutableLiveData<WeatherThemeState> = MutableLiveData()

    val weatherThemeState: LiveData<WeatherThemeState> get() = _weatherThemeState

    private val _locations: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val locations: LiveData<ArrayList<String>> = _locations


    private val _weather: MutableLiveData<WeatherForecast> = MutableLiveData()
    val weather: LiveData<WeatherForecast> get() = _weather

    private val _location: MutableLiveData<Location> = MutableLiveData()
    val location: LiveData<Location> = _location


    fun getWeatherByLocation(
        lat: Double = homeRepository.latitude!!.toDouble(),
        long: Double = homeRepository.longitude!!.toDouble()
    ) {
        request(source = { getWeatherByLocationUseCase.invoke(lat, long) }, onSuccess = {
            _weather.value = it
            when (it.current.weather.first().main) {
                RAIN -> {
                    _weatherThemeState.value = WeatherThemeState.RAIN
                }

                CLEAR -> {
                    _weatherThemeState.value = WeatherThemeState.CLEAR
                }

                SNOW -> {
                    _weatherThemeState.value = WeatherThemeState.SNOW
                }

                CLOUDS -> {
                    _weatherThemeState.value = WeatherThemeState.CLOUD
                }

                else -> {
                    _weatherThemeState.value = WeatherThemeState.UNDEFINED
                }
            }
            homeRepository.saveLatLng(it.lat, it.lon)
        })
    }


    fun getWeatherByQuery(query: String) {
        request(source = { getWeatherByQueryUseCase.invoke(query) }) {
            _location.value = it
            val lat = it.coord.lat
            val lon = it.coord.lon
            getWeatherByLocation(lat, lon)
        }
    }


    fun getSavedLocations() {
        val list = arrayListOf(
            "New-York",
            "Bishkek",
            "Moscow",
            "Osh"
        )
        _locations.value = list
    }


    companion object {
        const val RAIN = "Rain"
        const val SNOW = "Snow"
        const val CLEAR = "Clear"
        const val CLOUDS = "Clouds"
    }


}