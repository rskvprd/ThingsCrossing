package com.app.thingscrossing.feature_advertisement.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
