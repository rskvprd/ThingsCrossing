package com.app.thingscrossing.di

import android.content.Context
import com.app.thingscrossing.core.ApiAdapter
import com.app.thingscrossing.feature_account.data.remote.AccountApi
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_account.domain.use_case.DeleteAuthKeyUseCase
import com.app.thingscrossing.feature_account.domain.use_case.GetAuthKeyUseCase
import com.app.thingscrossing.feature_account.domain.use_case.GetCurrentUserProfileByTokenUseCase
import com.app.thingscrossing.feature_account.domain.use_case.GetUserProfileById
import com.app.thingscrossing.feature_account.domain.use_case.SaveAuthKeyUseCase
import com.app.thingscrossing.feature_account.domain.use_case.SignInUseCase
import com.app.thingscrossing.feature_account.domain.use_case.SignUpUseCase
import com.app.thingscrossing.feature_account.domain.use_case.UploadAvatar
import com.app.thingscrossing.feature_advertisement.data.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.use_case.*
import com.app.thingscrossing.feature_chat.data.remote.ChatApi
import com.app.thingscrossing.feature_chat.domain.repository.ChatRepository
import com.app.thingscrossing.feature_chat.domain.use_case.ChatUseCases
import com.app.thingscrossing.feature_chat.domain.use_case.GetMessagesByRoom
import com.app.thingscrossing.feature_chat.domain.use_case.GetMyRooms
import com.app.thingscrossing.feature_chat.domain.use_case.GetOrCreatePrivateRoom
import com.app.thingscrossing.feature_chat.domain.use_case.SendMessage
import com.app.thingscrossing.services.AuthService
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
    fun provideChatApi(): ChatApi {
        return ApiAdapter.buildChatApi()
    }

    @Provides
    @Singleton
    fun provideAdvertisementUseCases(
        repository: AdvertisementRepository,
        @ApplicationContext context: Context,
        authService: AuthService,
    ): AdvertisementUseCases {
        return AdvertisementUseCases(
            deleteAdvertisement = DeleteAdvertisementUseCase(repository),
            addAdvertisement = AddAdvertisementUseCase(repository),
            getAdvertisement = GetAdvertisementUseCase(repository),
            searchAdvertisements = SearchAdvertisementsUseCase(repository),
            uploadImageUseCase = UploadImageUseCase(repository, context),
            deleteImageUseCase = DeleteImageUseCase(repository),
            getMyAdvertisementList = GetMyAdvertisementList(authService, repository),
            updateAdvertisement = UpdateAdvertisement(repository, authService)
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
            getUserProfileByAuthKey = GetCurrentUserProfileByTokenUseCase(
                context = context,
                accountRepository = accountRepository
            ),
            getUserProfileById = GetUserProfileById(accountRepository),
            uploadAvatar = UploadAvatar(repository = accountRepository, context = context)
        )
    }

    @Provides
    @Singleton
    fun provideChatUseCases(
        @ApplicationContext context: Context,
        chatRepository: ChatRepository,
    ): ChatUseCases {
        return ChatUseCases(
            getMessagesByRoom = GetMessagesByRoom(
                repository = chatRepository,
                context = context
            ),
            getOrCreatePrivateRoom = GetOrCreatePrivateRoom(
                repository = chatRepository,
                context = context
            ),
            getMyRooms = GetMyRooms(
                repository = chatRepository,
                context = context
            ),
            sendMessage = SendMessage(
                repository = chatRepository,
                context = context
            ),
        )
    }
}