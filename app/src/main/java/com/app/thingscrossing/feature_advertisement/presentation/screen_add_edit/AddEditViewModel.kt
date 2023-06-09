package com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import com.app.thingscrossing.feature_advertisement.domain.model.Exchange
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import com.app.thingscrossing.feature_advertisement.navigation.AdvertisementScreen
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.util.AddEditPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class AddEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val advertisementUseCases: AdvertisementUseCases,
    private val accountUseCases: AccountUseCases,
) : ViewModel() {

    var uiState by mutableStateOf(AddEditState())
        private set

    val eventFlow = MutableSharedFlow<AddEditViewModelEvent>()

    val advertisementId: Int? = savedStateHandle[AdvertisementScreen.AddEdit.ID_KEY]

    init {
        initAdvertisement()
    }

    private fun sendEvent(event: AddEditViewModelEvent) {
        viewModelScope.launch {
            eventFlow.emit(event)
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.TitleChange -> {
                uiState = uiState.copy(
                    title = event.title
                )
            }

            is AddEditEvent.AddressChange -> {
                uiState = uiState.copy(
                    address = event.address
                )
            }

            is AddEditEvent.DescriptionChange -> {
                uiState = uiState.copy(
                    description = event.description
                )
            }

            is AddEditEvent.AddNewCurrency -> {
                val prices = uiState.prices

                if (prices.map { it.currency }.contains(event.currency)) {
                    throw IllegalStateException("Duplicated currencies")
                }

                uiState = uiState.copy(
                    prices = prices.toMutableList().apply {
                        add(AddEditPrice(currency = event.currency, value = ""))
                    }
                )
            }

            is AddEditEvent.ChangePrice -> {
                if (!AddEditPrice.isValid(event.price)) return
                val prices = uiState.prices.toMutableList()
                for (i in prices.indices) {
                    if (prices[i].currency == event.price.currency) {
                        prices[i] = prices[i].copy(value = event.price.value)
                        break
                    }
                }
                uiState = uiState.copy(
                    prices = prices
                )
            }

            is AddEditEvent.DeleteCurrency -> {
                uiState = uiState.copy(
                    prices = uiState.prices
                        .filter { it.currency != event.currency }
                )
            }

            is AddEditEvent.PickImage -> {
                uiState = uiState.copy(
                    currentImageUri = event.uri,
                    showAddImageDialog = false
                )
            }

            is AddEditEvent.DropImage -> {
                uiState = uiState.copy(
                    currentImageUri = null,
                    showAddImageDialog = false
                )
            }

            is AddEditEvent.UploadImage -> {
                advertisementUseCases.uploadImageUseCase(
                    uiState.currentImageUri
                        ?: throw IllegalStateException("Uploading photo before it pick")
                ).onEach { resource ->
                    uiState = when (resource) {
                        is Resource.Error -> {
                            uiState.copy(
                                errorId = resource.messageId,
                                isLoading = false,
                            )
                        }

                        is Resource.Loading -> {
                            Log.d("zRRR", resource.progression.toString())
                            uiState.copy(
                                uploadingProgress = resource.progression?.toFloat(),
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            uiState.copy(
                                isLoading = false,
                                uploadingProgress = null,
                                images = uiState.images.toMutableList().apply {
                                    add(
                                        ImageModel(
                                            url = resource.data?.url ?: throw IllegalStateException(
                                                "Server doesn't return image url"
                                            )
                                        )
                                    )
                                }
                            )
                        }
                    }
                }.launchIn(viewModelScope)
                uiState = uiState.copy(
                    currentImageUri = null,
                )
            }

            is AddEditEvent.UploadAdvertisement -> {
                accountUseCases.getAuthKeyUseCase().onEach { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            uiState = uiState.copy(
                                errorId = resource.messageId,
                                isLoading = true
                            )
                        }

                        is Resource.Loading -> {
                            uiState = uiState.copy(
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            uiState = uiState.copy(
                                isLoading = false,
                            )
                            val authKey: String = resource.data
                                ?: throw IllegalStateException("You should authorize before do anything that require auth key")
                            uploadAdvertisement(authKey)
                        }
                    }
                }.launchIn(viewModelScope)
            }

            is AddEditEvent.DismissError -> {
                uiState = uiState.copy(
                    errorId = null
                )
            }

            AddEditEvent.AddImageClick -> {
                uiState = uiState.copy(
                    showAddImageDialog = true
                )
            }

            AddEditEvent.DismissAddImageDialog -> {
                uiState = uiState.copy(
                    showAddImageDialog = false
                )
            }

            is AddEditEvent.UpdateAdvertisement -> {
                advertisementUseCases.updateAdvertisement(
                    title = uiState.title,
                    description = uiState.description,
                    prices = uiState.prices,
                    address = uiState.address,
                    images = uiState.images,
                    characteristics = uiState.characteristics,
                    exchanges = uiState.exchanges,
                    categories = uiState.categories,
                    id = advertisementId!!
                ).onEach { resource ->
                    when (resource) {
                        is Resource.Error -> uiState =
                            uiState.copy(errorId = resource.messageId!!, isLoading = false)

                        is Resource.Loading -> uiState = uiState.copy(isLoading = true)
                        is Resource.Success -> uiState =
                            uiState.copy(advertisementUploaded = true, isLoading = false)
                    }
                }.launchIn(viewModelScope)
            }

            AddEditEvent.AddExchange ->
                if (uiState.newExchange.isNotBlank()) {
                    uiState = uiState.copy(
                        exchanges = uiState.exchanges + Exchange(uiState.newExchange),
                        newExchange = ""
                    )
                }

            is AddEditEvent.ChangeNewExchange -> uiState =
                uiState.copy(newExchange = event.exchange)

            is AddEditEvent.DeleteExchange -> uiState =
                uiState.copy(exchanges = uiState.exchanges - event.exchange)
        }
    }

    private fun initAdvertisement() {
        if (advertisementId != null && advertisementId > 0) {
            println(advertisementId)
            advertisementUseCases.getAdvertisement(advertisementId).onEach { result ->
                uiState = when (result) {
                    is Resource.Error -> {
                        AddEditState(
                            errorId = result.messageId ?: R.string.unexpected_error
                        )
                    }

                    is Resource.Loading -> {
                        AddEditState(isLoading = true)
                    }

                    is Resource.Success -> {
                        val advertisement = result.data!!
                        AddEditState(
                            images = advertisement.images,
                            title = advertisement.title,
                            description = advertisement.description,
                            address = advertisement.address,
                            prices = advertisement.prices.map { AddEditPrice.fromPrice(it) },
                            characteristics = advertisement.characteristics,
                            exchanges = advertisement.exchanges,
                            categories = advertisement.categories,
                            isLoading = false,
                            isEdit = true,
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun uploadAdvertisement(authKey: String) {
        advertisementUseCases.addAdvertisement(
            title = uiState.title,
            description = uiState.description,
            prices = uiState.prices,
            address = uiState.address,
            images = uiState.images,
            characteristics = uiState.characteristics,
            exchanges = uiState.exchanges,
            categories = uiState.categories,
            authKey = authKey
        ).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    uiState = uiState.copy(
                        errorId = resource.messageId,
                        isLoading = false,
                    )
                }

                is Resource.Loading -> {
                    uiState = uiState.copy(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        advertisementUploaded = true,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}