package com.reviro.weather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.reviro.common.base.BaseViewModel
import com.reviro.domain.model.WeatherForecast
import com.reviro.domain.usecases.GetWeatherByLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase
) : BaseViewModel() {


    private val _weather: MutableLiveData<WeatherForecast> = MutableLiveData()
    val weather: LiveData<WeatherForecast> get() = _weather


    fun getWeatherByLocation(lat: Double, long: Double) {
        request(source = { getWeatherByLocationUseCase.invoke(lat, long) }) {
            _weather.value = it
        }
    }



}