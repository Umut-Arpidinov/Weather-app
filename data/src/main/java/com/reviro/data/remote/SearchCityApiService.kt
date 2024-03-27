package com.reviro.data.remote

import com.reviro.common.utils.AppConstants
import com.reviro.domain.model.Location
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchCityApiService {

    @GET("weather?")
    suspend fun getLocationByQuery(
        @Query("q") q: String,
        @Query("lan") lan: String = "ru",
        @Query("appid") appId: String = AppConstants.API_KEY
    ): Location
}