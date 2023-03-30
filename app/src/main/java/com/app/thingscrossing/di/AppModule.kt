package com.app.thingscrossing.di

import android.app.Application
import androidx.room.Room
import com.app.thingscrossing.feature_advertisement.data.source.local.AdvertisementDao
import com.app.thingscrossing.feature_advertisement.data.source.local.AdvertisementDatabase
import com.app.thingscrossing.feature_advertisement.data.source.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.use_case.*
import com.app.thingscrossing.feature_advertisement.data.source.remote.ApiAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAdvertisementApi(): AdvertisementApi {
        return ApiAdapter.buildAdvertisementApi()
    }

    @Provides
    @Singleton
    fun provideAdvertisementDatabase(app: Application): AdvertisementDatabase {
        return Room.databaseBuilder(
            app, AdvertisementDatabase::class.java, AdvertisementDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAdvertisementDao(advertisementDatabase: AdvertisementDatabase): AdvertisementDao {
        return advertisementDatabase.advertisementDao
    }

    @Provides
    @Singleton
    fun provideAdvertisementUseCases(repository: AdvertisementRepository): AdvertisementUseCases {
        return AdvertisementUseCases(
            getAdvertisementList = GetAdvertisementListUseCase(repository),
            deleteAdvertisement = DeleteAdvertisementUseCase(repository),
            addAdvertisement = AddAdvertisementUseCase(repository),
            getAdvertisement = GetAdvertisementUseCase(repository),
            searchAdvertisements = SearchAdvertisementsUseCase(repository)
        )
    }
}