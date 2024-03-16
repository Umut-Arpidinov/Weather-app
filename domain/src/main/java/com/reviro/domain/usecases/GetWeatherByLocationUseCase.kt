package com.reviro.domain.usecases

import com.reviro.common.base.network.ApiResult
import com.reviro.domain.interfaces.HomeRepository
import com.reviro.domain.model.WeatherForecast
import javax.inject.Inject


class GetWeatherByLocationUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke(lat: Double, long: Double): ApiResult<WeatherForecast> {
        return homeRepository.getWeatherByLocation(lat, long)
    }
}