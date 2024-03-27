package com.reviro.data.di

import com.reviro.data.repository.HomeRepositoryImpl
import com.reviro.data.repository.SearchRepositoryImpl
import com.reviro.domain.interfaces.HomeRepository
import com.reviro.domain.interfaces.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository


}