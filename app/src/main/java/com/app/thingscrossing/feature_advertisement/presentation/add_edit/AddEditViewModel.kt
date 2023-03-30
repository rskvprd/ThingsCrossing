package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.navigation.advertisementIdNavArgument
import com.app.thingscrossing.core.util.ViewModelWithRemoteCalls
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.Price
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import com.app.thingscrossing.feature_advertisement.presentation.search.ConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    repository: AdvertisementRepository,
    savedStateHandle: SavedStateHandle,
    useCases: AdvertisementUseCases,
) : ViewModel(), ViewModelWithRemoteCalls {
    private var advertisement: Advertisement? = null
    var uiState by mutableStateOf(AddEditState())
        private set

    override var connectionState: ConnectionState = ConnectionState.Ok
        set(value) {
            field = value
            uiState = uiState.copy(
                connectionState = value
            )
        }

    init {
        val advertisementId = savedStateHandle.get<Int>(advertisementIdNavArgument)
        advertisementId?.let {
            if (it > 0) {
                viewModelScope.launch {
                    val advertisement: Advertisement = remoteRequest {
                        useCases.getAdvertisement(it)
                    } ?: Advertisement.DEFAULT
                    uiState = uiState.copy(
                        advertisement = advertisement
                    )
                }
            }
        }
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
            is AddEditEvent.ToggleCurrencyEdit -> {
                uiState = uiState.copy(
                    isCurrencyEditVisible = !uiState.isCurrencyEditVisible
                )
            }
            is AddEditEvent.AddNewCurrency -> {
                val prices = uiState.advertisement.prices
                if (prices.map { it.currencyCode }.contains(event.currency)) {
                    throw IllegalStateException("Duplicated currencies")
                }
                uiState = uiState.copy(
                    isSetupCurrency = false,
                    advertisement = uiState.advertisement.copy(
                        prices = arrayListOf(
                            *(uiState.advertisement.prices.toTypedArray()),
                            Price(0f, event.currency)
                        )
                    )
                )
            }
            is AddEditEvent.ChangePrice -> TODO()
            is AddEditEvent.SetupPrice -> {
                uiState = uiState.copy(
                    isSetupCurrency = true
                )
            }
        }
    }
}