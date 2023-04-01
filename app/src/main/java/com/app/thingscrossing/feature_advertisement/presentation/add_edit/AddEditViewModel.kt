package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.core.navigation.advertisementIdNavArgument
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.Price
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val advertisementUseCases: AdvertisementUseCases,
) : ViewModel() {
    private var advertisement: Advertisement? = null

    var uiState by mutableStateOf(AddEditState())
        private set


    init {
        initAdvertisement()
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.TitleChange -> {
                advertisement = advertisement?.copy(title = event.title)
            }
            is AddEditEvent.AddressChange -> {
                advertisement = advertisement?.copy(address = event.address)
            }
            is AddEditEvent.DescriptionChange -> {
                advertisement = advertisement?.copy(description = event.description)
            }
            is AddEditEvent.AddNewCurrency -> {
                val prices = uiState.advertisement.prices
                if (prices.map { it.currency }.contains(event.currency)) {
                    throw IllegalStateException("Duplicated currencies")
                }
                uiState = uiState.copy(
                    advertisement = uiState.advertisement.copy(
                        prices = arrayListOf(
                            *(uiState.advertisement.prices.toTypedArray()),
                            Price(0.0, event.currency)
                        )
                    )
                )
            }
            is AddEditEvent.ChangePrice -> TODO()
            is AddEditEvent.DeleteCurrency -> {
                uiState = uiState.copy(
                    advertisement = uiState.advertisement.copy(
                        prices = uiState.advertisement.prices
                            .filter { it.currency != event.currency } as ArrayList<Price>
                    )
                )
            }
        }
    }

    fun initAdvertisement() {
        val advertisementId = savedStateHandle.get<Int>(advertisementIdNavArgument)
        if (advertisementId != null && advertisementId > 0) {
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
                        AddEditState(advertisement = result.data ?: Advertisement.DEFAULT)
                    }
                }
            }
        }
    }
}