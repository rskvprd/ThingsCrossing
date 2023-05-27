package com.app.thingscrossing.di

import com.app.thingscrossing.feature_account.data.repository.AccountRepositoryImpl
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import com.app.thingscrossing.feature_advertisement.data.repository.AdvertisementRepositoryImpl
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_chat.data.repository.ChatRepositoryImpl
import com.app.thingscrossing.feature_chat.domain.repository.ChatRepository
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

    @Binds
    @Singleton
    abstract fun bindAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        accountRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository
}