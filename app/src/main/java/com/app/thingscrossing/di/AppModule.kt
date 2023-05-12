package com.app.thingscrossing.di

import android.content.Context
import com.app.thingscrossing.feature_account.data.remote.AccountApi
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_account.domain.use_case.DeleteAuthKeyUseCase
import com.app.thingscrossing.feature_account.domain.use_case.GetAuthKeyUseCase
import com.app.thingscrossing.feature_account.domain.use_case.GetCurrentUserProfileByTokenUseCase
import com.app.thingscrossing.feature_account.domain.use_case.SaveAuthKeyUseCase
import com.app.thingscrossing.feature_account.domain.use_case.SignInUseCase
import com.app.thingscrossing.feature_account.domain.use_case.SignUpUseCase
import com.app.thingscrossing.feature_advertisement.data.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.data.remote.ApiAdapter
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.use_case.*
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
    fun provideAccountApi(): AccountApi {
        return ApiAdapter.buildAccountApi()
    }

    @Provides
    @Singleton
    fun provideAdvertisementUseCases(
        repository: AdvertisementRepository,
        @ApplicationContext context: Context
    ): AdvertisementUseCases {
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

    @Provides
    @Singleton
    fun provideAccountUseCases(
        @ApplicationContext context: Context,
        accountRepository: AccountRepository
    ): AccountUseCases {
        return AccountUseCases(
            saveAuthKeyUseCase = SaveAuthKeyUseCase(context = context),
            getAuthKeyUseCase = GetAuthKeyUseCase(context = context),
            signUpUseCase = SignUpUseCase(accountRepository = accountRepository),
            deleteAuthKeyUseCase = DeleteAuthKeyUseCase(context = context),
            signInUseCase = SignInUseCase(accountRepository = accountRepository, context = context),
            getCurrentUserProfileByTokenUseCase = GetCurrentUserProfileByTokenUseCase(
                context = context,
                accountRepository = accountRepository
            )
        )
    }
}