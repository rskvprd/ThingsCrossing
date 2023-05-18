package com.app.thingscrossing.feature_advertisement.presentation.search

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementSortVariant

sealed interface SearchEvent {
    object RefreshNetwork: SearchEvent

    object ApplyOrder: SearchEvent

    class ChangeSortVariant(val variant: AdvertisementSortVariant) : SearchEvent

    class ChangeSortOrder(val order: Boolean) : SearchEvent

    data class DeleteAd(val ad: Advertisement): SearchEvent

    data class Search(val searchValue: String) : SearchEvent

    object RestoreAd: SearchEvent

    object ToggleSortSection: SearchEvent

    object ToggleFilterSection: SearchEvent

    data class SearchValueChanged(val newSearchValue: String): SearchEvent

    object EraseSearchBox: SearchEvent

}

sealed interface SearchViewModelEvent {
    object ShowBottomSheet : SearchViewModelEvent
    object HideBottomSheet : SearchViewModelEvent
}