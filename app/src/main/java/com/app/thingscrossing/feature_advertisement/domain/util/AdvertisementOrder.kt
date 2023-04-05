package com.app.thingscrossing.feature_advertisement.domain.util

sealed class AdvertisementOrder {
    class Title(orderType: OrderType): AdvertisementOrder()
    class Date(orderType: OrderType): AdvertisementOrder()
}
