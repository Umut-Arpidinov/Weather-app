package com.reviro.data.repository

import com.reviro.common.base.network.ApiResult
import com.reviro.common.base.network.apiRequest
import com.reviro.data.remote.WeatherApiService
import com.reviro.domain.interfaces.HomeRepository
import com.reviro.domain.model.WeatherForecast
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
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
}