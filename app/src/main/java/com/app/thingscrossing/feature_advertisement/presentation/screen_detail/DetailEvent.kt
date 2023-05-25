package com.app.thingscrossing.feature_advertisement.presentation.screen_detail

sealed interface DetailEvent {
    object ToggleMorePricesVisibility : DetailEvent
}