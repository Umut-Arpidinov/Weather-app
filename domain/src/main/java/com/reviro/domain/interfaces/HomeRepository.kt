package com.reviro.domain.interfaces

import com.reviro.common.base.network.ApiResult
import com.reviro.domain.model.WeatherForecast


interface HomeRepository {

   suspend fun getWeatherByLocation(
        lat: Double,
        long: Double
    ): ApiResult<WeatherForecast>


   fun saveLatLng(lat: Double, long: Double)

   val hasSavedLatAndLong: Boolean

   val latitude: String?

   val longitude: String?

}