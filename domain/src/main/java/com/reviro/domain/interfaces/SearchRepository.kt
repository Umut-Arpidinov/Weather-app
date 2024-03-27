package com.reviro.domain.interfaces

import com.reviro.common.base.network.ApiResult
import com.reviro.domain.model.Location
import com.reviro.domain.model.WeatherForecast

interface SearchRepository {
    suspend fun getWeatherByQuery(
        q: String
    ): ApiResult<Location>
}