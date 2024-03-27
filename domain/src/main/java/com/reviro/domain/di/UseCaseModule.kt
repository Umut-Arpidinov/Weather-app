package com.reviro.domain.di

import com.reviro.domain.interfaces.HomeRepository
import com.reviro.domain.interfaces.SearchRepository
import com.reviro.domain.usecases.GetWeatherByLocationUseCase
import com.reviro.domain.usecases.GetWeatherByQueryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetWeatherByLocationUseCase(homeRepository: HomeRepository) =
        GetWeatherByLocationUseCase(homeRepository)


    @Provides
    fun provideGetWeatherByQueryUseCase(searchRepository: SearchRepository) =
        GetWeatherByQueryUseCase(searchRepository)
}