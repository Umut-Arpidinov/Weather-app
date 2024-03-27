package com.reviro.domain.usecases

import com.reviro.common.base.network.ApiResult
import com.reviro.domain.interfaces.SearchRepository
import com.reviro.domain.model.Location
import javax.inject.Inject

class GetWeatherByQueryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(query: String): ApiResult<Location> {
        return searchRepository.getWeatherByQuery(query)
    }
}