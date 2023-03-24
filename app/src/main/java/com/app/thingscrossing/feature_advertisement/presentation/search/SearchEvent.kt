package com.app.thingscrossing.feature_advertisement.presentation.search

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder

sealed class SearchEvent {
    data class Order(val adOrder: AdvertisementOrder): SearchEvent()
    data class DeleteAd(val ad: Advertisement): SearchEvent()
    data class Search(val request: String) : SearchEvent()

    object RestoreAd: SearchEvent()
    object ToggleOrderSection: SearchEvent()

}
