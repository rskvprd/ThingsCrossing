package com.app.thingscrossing.feature_advertisement.presentation.advertisements

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder
import com.app.thingscrossing.feature_advertisement.domain.util.OrderType

data class SearchState(
    val advertisements: List<Advertisement> = emptyList(),
    val advertisementOrder: AdvertisementOrder = AdvertisementOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
