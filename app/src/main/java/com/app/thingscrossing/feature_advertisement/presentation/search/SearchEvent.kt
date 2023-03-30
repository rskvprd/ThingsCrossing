package com.app.thingscrossing.feature_advertisement.presentation.search

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder

sealed interface SearchEvent {
    object RefreshNetwork: SearchEvent

    data class Order(val adOrder: AdvertisementOrder): SearchEvent

    data class DeleteAd(val ad: Advertisement): SearchEvent

    data class Search(val searchValue: String) : SearchEvent

    object RestoreAd: SearchEvent

    object ToggleSortSection: SearchEvent

    object ToggleFilterSection: SearchEvent

    data class SearchValueChanged(val newSearchValue: String): SearchEvent

    object EraseSearchBox: SearchEvent


}
