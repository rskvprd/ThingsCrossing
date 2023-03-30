package com.app.thingscrossing.feature_advertisement.presentation.search

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.core.util.ViewModelWithRemoteCalls
import com.app.thingscrossing.feature_advertisement.data.util.isNetworkAvailable
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder
import com.app.thingscrossing.feature_advertisement.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: AdvertisementRepository,
    private val advertisementUseCases: AdvertisementUseCases,
    private val context: Application,
) : ViewModel(), ViewModelWithRemoteCalls {
    override var connectionState: ConnectionState = ConnectionState.Ok
        set(value) {
            field = value
            uiState = uiState.copy(
                connectionState = value
            )
        }

    var uiState by mutableStateOf(SearchState())
        private set

    private var recentlyDeletedAd: Advertisement? = null

    private var makeRequestJob: Job? = null

    init {
        if (context.isNetworkAvailable) {
            viewModelScope.launch {
                val advertisements = remoteRequest {
                    advertisementUseCases.getAdvertisementList(AdvertisementOrder.Date(OrderType.Descending))
                } ?: emptyList()
                uiState = uiState.copy(
                    advertisements = advertisements
                )
            }
        } else {
            uiState = uiState.copy(
                connectionState = ConnectionState.NetworkUnavailable
            )
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.DeleteAd -> {
                viewModelScope.launch {
                    remoteRequest {
                        advertisementUseCases.deleteAdvertisement(event.ad)
                    }
                    recentlyDeletedAd = event.ad
                }
            }
            is SearchEvent.Order -> {
                if (uiState.advertisementOrder::class == event.adOrder::class && uiState.advertisementOrder.orderType == event.adOrder.orderType) {
                    return
                }
            }
            is SearchEvent.RestoreAd -> {
                viewModelScope.launch {
                    remoteRequest {
                        advertisementUseCases.addAdvertisement(
                            recentlyDeletedAd ?: return@remoteRequest
                        )
                    }
                    recentlyDeletedAd = null
                }
            }
            is SearchEvent.Search -> {
                viewModelScope.launch {
                    val foundAdvertisements: List<Advertisement> = remoteRequest {
                        advertisementUseCases.searchAdvertisements(event.searchValue)
                    } ?: emptyList()
                    uiState = uiState.copy(
                        advertisements = foundAdvertisements
                    )
                }
            }
            is SearchEvent.SearchValueChanged -> {
                val validated = validateSearchValue(event.newSearchValue)
                uiState = uiState.copy(
                    searchValue = validated, isEraseIconVisible = validated.isNotBlank()
                )
            }
            is SearchEvent.EraseSearchBox -> {
                uiState = uiState.copy(
                    searchValue = "",
                    isEraseIconVisible = false,
                )
            }
            is SearchEvent.ToggleSortSection -> {
                TODO()
            }
            is SearchEvent.ToggleFilterSection -> {
                TODO()
            }
            SearchEvent.RefreshNetwork -> {
                TODO()
            }
        }
    }

    private fun validateSearchValue(searchValue: String): String {
        if (searchValue.isBlank()) return ""
        return searchValue.replace("  ", " ")
    }
}