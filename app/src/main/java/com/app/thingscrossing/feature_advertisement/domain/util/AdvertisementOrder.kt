package com.app.thingscrossing.feature_advertisement.domain.util

sealed class AdvertisementOrder (val orderType: OrderType) {
    class Title(orderType: OrderType): AdvertisementOrder(orderType)
    class Date(orderType: OrderType): AdvertisementOrder(orderType)
}
