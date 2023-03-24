package com.app.thingscrossing.feature_advertisement.presentation.search

import android.app.Application
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder
import com.app.thingscrossing.feature_advertisement.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: AdvertisementRepository,
    private val advertisementUseCases: AdvertisementUseCases,
    private val context: Application,
) : ViewModel() {

    var uiState by mutableStateOf(SearchState())
        private set

    private var recentlyDeletedAd: Advertisement? = null

    private var getAdvertisementsJob: Job? = null

    init {
        getAdvertisements(AdvertisementOrder.Date(OrderType.Descending))
    }


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.DeleteAd -> {
                viewModelScope.launch {
                    advertisementUseCases.deleteAdvertisement(event.ad)
                    recentlyDeletedAd = event.ad
                }
            }
            is SearchEvent.Order -> {
                if (uiState.advertisementOrder::class == event.adOrder::class &&
                    uiState.advertisementOrder.orderType == event.adOrder.orderType
                ) {
                    return
                }
            }
            is SearchEvent.RestoreAd -> {
                viewModelScope.launch {
                    advertisementUseCases.addAdvertisement(recentlyDeletedAd ?: return@launch)
                    recentlyDeletedAd = null
                }
            }
            is SearchEvent.ToggleOrderSection -> {
                TODO()
            }
            is SearchEvent.Search -> {
                viewModelScope.launch {
                    val advertisements = advertisementUseCases.searchAdvertisements(event.request)
                    uiState = uiState.copy(
                        advertisements = advertisements
                    )
                }
            }
        }
    }

    private fun getAdvertisements(advertisementOrder: AdvertisementOrder) {
        getAdvertisementsJob?.cancel()
        getAdvertisementsJob = viewModelScope.launch {
            advertisementUseCases.getAdvertisementList(advertisementOrder).apply {
                uiState = uiState.copy(
                    advertisements = this@apply,
                    advertisementOrder = advertisementOrder
                )
            }

        }
    }

}