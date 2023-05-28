package com.app.thingscrossing.feature_advertisement.presentation.screen_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.core.navigation.advertisementIdNavArgument
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import com.app.thingscrossing.feature_chat.domain.use_case.ChatUseCases
import com.app.thingscrossing.feature_chat.presentation.util.ChatScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val advertisementUseCases: AdvertisementUseCases,
    private val chatUseCases: ChatUseCases,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var uiState by mutableStateOf(DetailState())
        private set

    var eventFlow = MutableSharedFlow<DetailViewModelEvent>()
        private set

    init {
        val advertisementId = savedStateHandle.get<Int>(advertisementIdNavArgument)
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
                chatUseCases.getOrCreatePrivateRoom(event.profile).onEach { resource ->
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
                                            "?userId=${event.profile.id}&roomId=${room.id}"
                                )
                            )

                            uiState = uiState.copy(
                                isLoading = false,
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}