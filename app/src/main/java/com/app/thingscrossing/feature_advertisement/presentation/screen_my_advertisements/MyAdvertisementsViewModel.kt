package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import com.app.thingscrossing.feature_advertisement.navigation.AdvertisementScreen
import com.app.thingscrossing.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAdvertisementsViewModel @Inject constructor(
    private val authService: AuthService,
    private val advertisementUseCases: AdvertisementUseCases,
) : ViewModel() {

    var uiState by mutableStateOf(MyAdvertisementsState())
        private set

    val eventFlow = MutableSharedFlow<MyAdvertisementsViewModelEvent>()

    init {
        getMyAdvertisements()
    }

    private fun sendEvent(event: MyAdvertisementsViewModelEvent) {
        viewModelScope.launch {
            eventFlow.emit(event)
        }
    }

    fun onEvent(event: MyAdvertisementsEvent) {
        when (event) {
            MyAdvertisementsEvent.DismissError -> uiState = uiState.copy(errorMessageId = null)
            MyAdvertisementsEvent.AddAdvertisement -> {
                viewModelScope.launch {
                    while (!authService.isInitialized) {
                        delay(10)
                    }
                    if (authService.isAuthenticated) {
                        sendEvent(MyAdvertisementsViewModelEvent.Navigate(AdvertisementScreen.AddEdit.DEFAULT_ROUTE))
                    } else {
                        uiState = uiState.copy(
                            errorMessageId = R.string.sign_in_before_create_advertisement
                        )
                    }
                }
            }

            is MyAdvertisementsEvent.EditAdvertisement -> {
                viewModelScope.launch {
                    sendEvent(
                        MyAdvertisementsViewModelEvent.Navigate(
                            AdvertisementScreen.AddEdit(event.advertisementId).route
                        )
                    )
                }
            }
        }
    }

    private fun getMyAdvertisements() {
        viewModelScope.launch {
            while (!authService.isInitialized) {
                delay(10)
            }
            if (!authService.isAuthenticated) {
                uiState = uiState.copy(isAuthorized = false)
            } else {
                uiState = uiState.copy(isAuthorized = true)
                advertisementUseCases.getMyAdvertisementList().onEach { resource ->
                    when (resource) {
                        is Resource.Error -> uiState =
                            uiState.copy(errorMessageId = resource.messageId!!, isLoading = false)

                        is Resource.Loading -> uiState = uiState.copy(isLoading = true)

                        is Resource.Success -> {
                            uiState =
                                uiState.copy(
                                    myAdvertisementList = resource.data!!,
                                    isLoading = false,
                                )
                        }
                    }
                }.collect()
            }
        }
    }
}