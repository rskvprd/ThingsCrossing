package com.app.thingscrossing.services

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.core.addBaseUrl
import com.app.thingscrossing.core.util.isNetworkAvailable
import com.app.thingscrossing.feature_account.data.local.AUTH_KEY
import com.app.thingscrossing.feature_account.data.local.authDataStore
import com.app.thingscrossing.feature_account.data.remote.dto.SignUpResponse
import com.app.thingscrossing.feature_account.domain.model.User
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthService @Inject constructor(
    private val accountUseCases: AccountUseCases,
    @ApplicationContext private val context: Context
) {

    var currentUserProfile: UserProfile? = null

    var authKey: String? = null
        private set

    val isAuthenticated
        get() = authKey != null

    var isInitialized = false
        private set

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        loadProfileAndAuthKey()
    }

    fun signInWithUsernameAndPassword(
        username: String,
        password: String,
        onSuccess: (profile: UserProfile) -> Unit = {},
        onError: (errorId: Int) -> Unit = {},
        onLoading: () -> Unit = {},
    ) {

        scope.launch {
            if (!isInitialized) delay(10)
            accountUseCases.signInUseCase(
                User(
                    username = username,
                    password = password
                )
            ).onEach { authKeyResource ->
                when (authKeyResource) {
                    is Resource.Error -> {
                        onError(authKeyResource.messageId!!)
                    }

                    is Resource.Loading -> {
                        onLoading()
                    }

                    is Resource.Success -> {
                        authKey = authKeyResource.data!!
                        saveAuthKey(authKey!!)

                        accountUseCases.getUserProfileByAuthKey(authKey = authKey!!)
                            .onEach { profileResource ->
                                when (profileResource) {
                                    is Resource.Error -> {
                                        throw IllegalStateException("Correct obtain token but can't obtain UserProfile")
                                    }

                                    is Resource.Loading -> {}

                                    is Resource.Success -> {
                                        val profile = profileResource.data!!
                                        currentUserProfile = profile
                                        onSuccess(currentUserProfile!!)
                                    }
                                }
                            }.collect()
                    }
                }
            }.collect()
        }
    }

    fun signUpWithCredentials(
        username: String,
        firstName: String,
        lastName: String,
        password: String,
        email: String,
        onSuccess: (profile: UserProfile) -> Unit = {},
        onLoading: () -> Unit = {},
        onError: (errorId: Int) -> Unit = {},
    ) {
        scope.launch {
            if (!isInitialized) {
                delay(10)
            }
            accountUseCases.signUpUseCase(
                User(
                    username = username,
                    password = password,
                    lastName = lastName,
                    firstName = firstName,
                    email = email,
                )
            ).onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        onError(resource.messageId!!)
                    }

                    is Resource.Loading -> {
                        onLoading()
                    }

                    is Resource.Success -> {
                        val response: SignUpResponse = resource.data!!
                        authKey = response.token
                        saveAuthKey(authKey!!)
                        currentUserProfile = response.profile.copy(
                            avatar = response.profile.avatar.addBaseUrl()
                        )
                        onSuccess(currentUserProfile!!)
                    }
                }
            }.collect()
        }
    }

    fun signOut() {
        authKey = null
        currentUserProfile = null

        scope.launch {
            context.authDataStore.edit {
                it.clear()
            }
        }
    }


    private suspend fun getLocalAuthKey(): String? {
        return context.authDataStore.data.first()[AUTH_KEY]
    }

    private suspend fun saveAuthKey(authKey: String) {
        context.authDataStore.edit {
            it[AUTH_KEY] = authKey
        }
    }

    private fun loadProfileAndAuthKey() {
        scope.launch {
            authKey = getLocalAuthKey()

            if (authKey != null) {
                if (!context.isNetworkAvailable) {
                    isInitialized = false
                    return@launch
                }
                accountUseCases.getUserProfileByAuthKey(authKey = authKey!!)
                    .onEach { profileResource ->
                        when (profileResource) {
                            is Resource.Error -> {
                                when (profileResource.messageId) {
                                    R.string.user_with_this_auth_key_does_not_exist -> {
                                        signOut()
                                    }
                                    else -> {
                                        isInitialized = false
                                    }
                                }
                            }

                            is Resource.Loading -> {}

                            is Resource.Success -> {
                                val profile = profileResource.data!!
                                currentUserProfile = profile
                                isInitialized = true
                            }
                        }
                    }.collect()
            } else {
                isInitialized = true
            }
        }
    }
}
