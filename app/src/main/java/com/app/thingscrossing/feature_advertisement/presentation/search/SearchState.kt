package com.app.thingscrossing.feature_advertisement.presentation.search

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder
import com.app.thingscrossing.feature_advertisement.domain.util.OrderType

data class SearchState(
    /**Don't change it manually*/
    val connectionState: ConnectionState = ConnectionState.Ok,
    val isOrderSectionVisible: Boolean = false,
    val isEraseIconVisible: Boolean = false,
    val searchValue: String = "",
    val advertisements: List<Advertisement> = emptyList(),
    val advertisementOrder: AdvertisementOrder = AdvertisementOrder.Date(OrderType.Descending),
)

sealed interface ConnectionState {
    object NetworkUnavailable: ConnectionState
    object RefusedConnection: ConnectionState
    object ServerTimeOut: ConnectionState
    object Ok: ConnectionState
    object Loading: ConnectionState
}