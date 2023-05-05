package com.app.thingscrossing.feature_advertisement.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val advertisementUseCases: AdvertisementUseCases,
) : ViewModel() {
    var uiState by mutableStateOf(SearchState())
        private set

    private var recentlyDeletedAd: Advertisement? = null

    init {
        getAdvertisementList()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.DeleteAd -> {
                advertisementUseCases.deleteAdvertisement(event.ad).onEach {
                    when (it) {
                        is Resource.Error -> TODO()
                        is Resource.Loading -> TODO()
                        is Resource.Success -> TODO()
                    }
                }.launchIn(viewModelScope)
                recentlyDeletedAd = event.ad
            }
            is SearchEvent.Order -> {
                TODO()
            }
            is SearchEvent.RestoreAd -> {
                advertisementUseCases.addAdvertisement(recentlyDeletedAd ?: return)
                recentlyDeletedAd = null
            }
            is SearchEvent.Search -> {
                getAdvertisementList(event.searchValue)
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
            is SearchEvent.RefreshNetwork -> {
                getAdvertisementList(uiState.searchValue)
            }
        }
    }

    private fun validateSearchValue(searchValue: String): String {
        if (searchValue.isBlank()) return ""
        return searchValue.replace("  ", " ")
    }

    private fun getAdvertisementList(searchValue: String? = null) {
        if (searchValue.isNullOrBlank()) {
            advertisementUseCases.getAdvertisementList().onEach { result ->
                uiState = when (result) {
                    is Resource.Error -> {
                        uiState.copy(
                            isLoading = false,
                            errorId = result.messageId ?: R.string.unexpected_error
                        )
                    }
                    is Resource.Loading -> {
                        uiState.copy(
                            isLoading = true,
                            errorId = null,
                        )
                    }
                    is Resource.Success -> {
                        uiState.copy(
                            advertisements = result.data ?: emptyList(),
                            isLoading = false,
                            errorId = null,
                        )
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            advertisementUseCases.searchAdvertisements(searchValue).onEach { result ->
                uiState = when (result) {
                    is Resource.Error -> {
                        uiState.copy(
                            isLoading = false,
                            errorId = result.messageId ?: R.string.unexpected_error
                        )
                    }
                    is Resource.Loading -> {
                        uiState.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        uiState.copy(
                            advertisements = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}