package com.reviro.data.remote

import com.reviro.common.utils.AppConstants
import com.reviro.domain.model.Location
import com.reviro.domain.model.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherApiService {

    @GET("data/3.0/onecall")
    suspend fun getWeatherByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = "daily,minutely",
        @Query("appid") apiKey: String = AppConstants.API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lan: String = "ru"
    ): WeatherForecast






}