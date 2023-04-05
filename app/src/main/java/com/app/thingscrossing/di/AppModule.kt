package com.app.thingscrossing.di

import android.content.Context
import com.app.thingscrossing.feature_advertisement.data.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.use_case.*
import com.app.thingscrossing.feature_advertisement.data.remote.ApiAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAdvertisementUseCases(repository: AdvertisementRepository, @ApplicationContext context: Context): AdvertisementUseCases {
        return AdvertisementUseCases(
            getAdvertisementList = GetAdvertisementListUseCase(repository),
            deleteAdvertisement = DeleteAdvertisementUseCase(repository),
            addAdvertisement = AddAdvertisementUseCase(repository),
            getAdvertisement = GetAdvertisementUseCase(repository),
            searchAdvertisements = SearchAdvertisementsUseCase(repository),
            uploadImageUseCase = UploadImageUseCase(repository, context),
            deleteImageUseCase = DeleteImageUseCase(repository),
        )
    }
}