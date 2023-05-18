package com.app.thingscrossing.feature_advertisement.presentation.detail

sealed interface DetailEvent {
    object ToggleMorePricesVisibility : DetailEvent
}