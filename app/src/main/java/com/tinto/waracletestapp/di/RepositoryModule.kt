package com.tinto.waracletestapp.di

import com.tinto.waracletestapp.repository.CakesRepository
import com.tinto.waracletestapp.service.CakesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideImageRepository(cakesApiService: CakesApiService): CakesRepository {
        return CakesRepository(cakesApiService)
    }
}