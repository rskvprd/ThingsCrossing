package com.app.thingscrossing.di

import com.app.thingscrossing.feature_advertisement.data.repository.AdvertisementRepositoryImpl
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAdvertisementRepository(
        thingsCrossingRepositoryImpl: AdvertisementRepositoryImpl
    ): AdvertisementRepository
}