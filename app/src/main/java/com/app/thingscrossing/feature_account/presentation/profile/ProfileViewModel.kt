package com.app.thingscrossing.feature_account.presentation.profile

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_account.navigation.AccountScreens
import com.app.thingscrossing.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class ProfileViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases,
    @ApplicationContext private val context: Context,
    private val authService: AuthService
) : ViewModel() {

    var uiState by mutableStateOf(ProfileState())
        private set

    var eventFlow = MutableSharedFlow<ProfileViewModelEvent>()
        private set

    var currentUserProfile = authService.currentUserProfile

    private fun sendEvent(event: ProfileViewModelEvent) {
        viewModelScope.launch {
            eventFlow.emit(event)
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.DismissError -> {
                uiState = uiState.copy(
                    errorMessageId = null
                )
            }

            ProfileEvent.AddImageClick -> {
                uiState = uiState.copy(
                    showAddImageDialog = true
                )
            }

            ProfileEvent.DismissAddImageDialog -> {
                uiState = uiState.copy(
                    showAddImageDialog = false
                )
            }

            ProfileEvent.DropImage -> {
                uiState = uiState.copy(
                    currentImageUri = null,
                    showAddImageDialog = false,
                )
            }

            is ProfileEvent.PickImage -> {
                uiState = uiState.copy(
                    currentImageUri = event.uri,
                    showAddImageDialog = false,
                )
            }

            is ProfileEvent.UploadImage -> {
                accountUseCases.uploadAvatar(
                    userId = authService.currentUserProfile!!.id,
                    avatarUri = uiState.currentImageUri
                        ?: throw IllegalStateException("Uploading photo before it pick")
                ).onEach { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            uiState = uiState.copy(
                                errorMessageId = resource.messageId,
                                isLoading = false,
                            )
                        }

                        is Resource.Loading -> {
                            uiState = uiState.copy(
                                uploadingAvatarProgress = resource.progression?.toFloat(),
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            uiState = uiState.copy(
                                isLoading = false,
                                uploadingAvatarProgress = null,
                            )
                            currentUserProfile = currentUserProfile!!.copy(
                                avatar = resource.data!!.avatar
                            )
                            authService.currentUserProfile = authService.currentUserProfile!!.copy(
                                avatar = resource.data.avatar
                            )
                        }
                    }
                }.launchIn(viewModelScope)
                uiState = uiState.copy(
                    currentImageUri = null,
                )
            }

            ProfileEvent.SignOut -> {
                sendEvent(ProfileViewModelEvent.Navigate(AccountScreens.SignInScreen.route))
                authService.signOut()
            }

            ProfileEvent.DismissSignOutDialog -> uiState =
                uiState.copy(showConfirmSignOutDialog = false)


            ProfileEvent.ShowConfirmSignOutDialog -> uiState =
                uiState.copy(showConfirmSignOutDialog = true)
        }
    }
}