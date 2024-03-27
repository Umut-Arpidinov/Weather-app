package com.reviro.data.repository

import com.reviro.common.base.network.ApiResult
import com.reviro.common.base.network.apiRequest
import com.reviro.data.remote.SearchCityApiService
import com.reviro.domain.interfaces.SearchRepository
import com.reviro.domain.model.Location
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchCityApiService
) : SearchRepository {

    override suspend fun getWeatherByQuery(q: String): ApiResult<Location> {
        return apiRequest { searchService.getLocationByQuery(q) }
    }

}