package com.app.thingscrossing.feature_advertisement.presentation.detail

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    advertisementUseCases: AdvertisementUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var uiState by mutableStateOf(DetailState())

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

}