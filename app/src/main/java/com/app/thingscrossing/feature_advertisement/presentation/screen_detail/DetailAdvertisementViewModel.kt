package com.app.thingscrossing.feature_advertisement.presentation.screen_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import com.app.thingscrossing.feature_advertisement.navigation.AdvertisementScreen
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import com.app.thingscrossing.feature_chat.domain.use_case.ChatUseCases
import com.app.thingscrossing.feature_chat.navigation.ChatScreens
import com.app.thingscrossing.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAdvertisementViewModel @Inject constructor(
    private val advertisementUseCases: AdvertisementUseCases,
    private val chatUseCases: ChatUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val authService: AuthService,
) : ViewModel() {
    var uiState by mutableStateOf(DetailState())
        private set

    var eventFlow = MutableSharedFlow<DetailViewModelEvent>()
        private set

    init {
        val advertisementId = savedStateHandle.get<Int>(AdvertisementScreen.Detail.ID_KEY)
        if (advertisementId != null) {
            advertisementUseCases.getAdvertisement(advertisementId).onEach { result ->
                uiState = when (result) {
                    is Resource.Error -> {
                        DetailState(errorId = result.messageId ?: R.string.unexpected_error)
                    }

                    is Resource.Loading -> {
                        DetailState(isLoading = true)
                    }

                    is Resource.Success -> {
                        DetailState(advertisement = result.data ?: Advertisement.DEFAULT)
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            uiState = DetailState(errorId = R.string.unexpected_error)
        }
    }

    private fun sendEvent(event: DetailViewModelEvent) {
        viewModelScope.launch {
            eventFlow.emit(event)
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            DetailEvent.ToggleMorePricesVisibility -> {
                uiState = uiState.copy(
                    isOtherPricesVisible = !uiState.isOtherPricesVisible
                )
            }

            is DetailEvent.ToChat -> {
                viewModelScope.launch {
                    while (!authService.isInitialized) delay(10)

                    if (authService.isAuthenticated) {
                        createRoomAndGoToChat(event.profile)
                    } else {
                        uiState = uiState.copy(
                            errorId = R.string.sign_in_before_conversation
                        )
                    }
                }
            }

            DetailEvent.DismissError -> uiState = uiState.copy(errorId = null)
        }
    }

    private suspend fun createRoomAndGoToChat(profile: UserProfile) {
        chatUseCases.getOrCreatePrivateRoom(profile).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    uiState = uiState.copy(
                        errorId = resource.messageId,
                        isLoading = false,
                    )
                }

                is Resource.Loading -> {
                    uiState = uiState.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    val room: ChatRoom = resource.data!!

                    sendEvent(
                        DetailViewModelEvent.Navigate(
                            route = ChatScreens.ChatScreen.route +
                                    "?userId=${profile.id}&roomId=${room.id}"
                        )
                    )

                    uiState = uiState.copy(
                        isLoading = false,
                    )
                }
            }
        }.collect()
    }
}