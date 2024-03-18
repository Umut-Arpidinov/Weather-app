package com.reviro.weather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.reviro.common.base.BaseViewModel
import com.reviro.domain.interfaces.HomeRepository
import com.reviro.domain.model.WeatherForecast
import com.reviro.domain.usecases.GetWeatherByLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val homeRepository: HomeRepository
) : BaseViewModel() {


    enum class WeatherThemeState {
        SNOW,
        RAIN,
        CLEAR,
        CLOUD,
    }


    private val _weatherThemeState: MutableLiveData<WeatherThemeState> = MutableLiveData()

    val weatherThemeState: LiveData<WeatherThemeState> get() = _weatherThemeState


    private val _weather: MutableLiveData<WeatherForecast> = MutableLiveData()
    val weather: LiveData<WeatherForecast> get() = _weather


    fun getWeatherByLocation(lat: Double, long: Double) {
        request(
            source = { getWeatherByLocationUseCase.invoke(lat, long) },
            onSuccess = {
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
                }
                homeRepository.saveLatLng(it.lat, it.lon)
            })
    }


    companion object {
        const val RAIN = "Rain"
        const val SNOW = "Snow"
        const val CLEAR = "Clear"
        const val CLOUDS = "Clouds"
    }


}