package com.app.thingscrossing.feature_advertisement.presentation.screen_search

import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementSortVariant
import com.app.thingscrossing.feature_advertisement.presentation.util.FilterOption

sealed interface SearchEvent {
    object RefreshNetwork : SearchEvent

    object DismissError: SearchEvent

    object ApplyOrder : SearchEvent

    class ChangeSortVariant(val variant: AdvertisementSortVariant) : SearchEvent

    class ChangeSortOrder(val order: Boolean) : SearchEvent


    data class Search(val searchValue: String) : SearchEvent


    object ToggleSortSection : SearchEvent

    object ToggleFilterSection : SearchEvent

    data class SearchValueChanged(val newSearchValue: String) : SearchEvent

    object EraseSearchBox : SearchEvent

    class ToggleFilterOption(val option: FilterOption): SearchEvent

    object Filter: SearchEvent
}

sealed interface SearchViewModelEvent {
    object ShowBottomSheet : SearchViewModelEvent
    object HideBottomSheet : SearchViewModelEvent

    class Navigate(val route: String) : SearchViewModelEvent
}