package com.reviro.data.di

import com.reviro.data.local.prefs.AuthLocaleSource
import com.reviro.data.local.prefs.AuthLocaleSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalSourceModule {

    @Binds
    abstract fun bindAuthLocaleSource(impl: AuthLocaleSourceImpl): AuthLocaleSource
}