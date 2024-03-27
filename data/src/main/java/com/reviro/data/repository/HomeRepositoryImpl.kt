package com.reviro.data.repository

import com.reviro.common.base.network.ApiResult
import com.reviro.common.base.network.apiRequest
import com.reviro.data.local.prefs.AuthLocaleSource
import com.reviro.data.remote.SearchCityApiService
import com.reviro.data.remote.WeatherApiService
import com.reviro.domain.interfaces.HomeRepository
import com.reviro.domain.model.WeatherForecast
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val authLocaleSource: AuthLocaleSource
) : HomeRepository {


    override suspend fun getWeatherByLocation(
        lat: Double,
        long: Double
    ): ApiResult<WeatherForecast> {
        return apiRequest {
            weatherApiService.getWeatherByLocation(
                lat, long
            )
        }
    }

    override fun saveLatLng(lat: Double, long: Double) {
        authLocaleSource.latitude = lat.toString()
        authLocaleSource.longitude = long.toString()
    }

    override val hasSavedLatAndLong: Boolean
        get() = authLocaleSource.hasSavedLatLng


    override val latitude: String?
        get() = authLocaleSource.latitude
    override val longitude: String?
        get() = authLocaleSource.longitude
}