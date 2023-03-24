package com.app.thingscrossing.di

import android.app.Application
import androidx.room.Room
import com.app.thingscrossing.feature_advertisement.data.source.local.AdvertisementDao
import com.app.thingscrossing.feature_advertisement.data.source.local.AdvertisementDatabase
import com.app.thingscrossing.feature_advertisement.data.source.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.use_case.*
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAdvertisementApi(): AdvertisementApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.50.161:8000")
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create()
                    )
            ).build().create(AdvertisementApi::class.java)
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