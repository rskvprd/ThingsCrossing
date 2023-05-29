package com.app.thingscrossing.di

import android.content.Context
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {
    @Provides
    @Singleton
    fun provideAuthService(
        accountUseCases: AccountUseCases,
        @ApplicationContext context: Context
    ): AuthService {
        return AuthService(accountUseCases = accountUseCases, context = context)
    }
}