package com.app.thingscrossing.feature_advertisement.presentation.detail_advertisement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.navigation.advertisementIdNavArgument
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAdvertisementViewModel @Inject constructor(
    advertisementUseCases: AdvertisementUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var uiState by mutableStateOf(DetailAdvertisementState())

    init {
        savedStateHandle.get<Int>(advertisementIdNavArgument)?.let { adId ->
            viewModelScope.launch {
                val advertisement = async {
                    advertisementUseCases.getAdvertisement(adId)
                }
                uiState = uiState.copy(
                    isLoading = true
                )
                uiState = uiState.copy(
                    advertisement = advertisement.await(),
                    isLoading = false
                )
            }
        }
    }

}