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
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementSortVariant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val advertisementUseCases: AdvertisementUseCases,
) : ViewModel() {
    var uiState by mutableStateOf(SearchState())
        private set

    var eventChannel = MutableSharedFlow<SearchViewModelEvent>()
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

            is SearchEvent.ApplyOrder -> {
                getAdvertisementList(
                    searchValue = uiState.searchValue,
                    sortVariant = uiState.sortVariant,
                    isSortAscending = uiState.isAscendingSort
                )
                sendEvent(SearchViewModelEvent.HideBottomSheet)
            }

            is SearchEvent.RestoreAd -> {
                TODO()
//                advertisementUseCases.addAdvertisement(recentlyDeletedAd ?: return)
//                recentlyDeletedAd = null
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
                uiState = uiState.copy(
                    currentBottomSheet = BottomSheet.SortBottomSheet
                )
                sendEvent(SearchViewModelEvent.ShowBottomSheet)
            }

            is SearchEvent.ToggleFilterSection -> {
                uiState = uiState.copy(
                    currentBottomSheet = BottomSheet.FilterBottomSheet
                )
                sendEvent(SearchViewModelEvent.ShowBottomSheet)
            }

            is SearchEvent.RefreshNetwork -> {
                uiState = uiState.copy(
                    errorId = null
                )
                getAdvertisementList(uiState.searchValue)
            }

            is SearchEvent.ChangeSortVariant -> {
                uiState = uiState.copy(
                    sortVariant = event.variant
                )
            }

            is SearchEvent.ChangeSortOrder -> {
                uiState = uiState.copy(
                    isAscendingSort = event.order
                )
            }
        }
    }

    private fun sendEvent(event: SearchViewModelEvent) {
        viewModelScope.launch {
            eventChannel.emit(event)
        }
    }

    private fun validateSearchValue(searchValue: String): String {
        if (searchValue.isBlank()) return ""
        return searchValue.replace("  ", " ")
    }

    private fun getAdvertisementList(
        searchValue: String? = null,
        sortVariant: AdvertisementSortVariant = AdvertisementSortVariant.Date,
        isSortAscending: Boolean = false,
    ) {
        advertisementUseCases.searchAdvertisements(
            searchValue = searchValue ?: "",
            advertisementSortVariant = sortVariant,
            isSortAscending = isSortAscending
        ).onEach { result ->
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