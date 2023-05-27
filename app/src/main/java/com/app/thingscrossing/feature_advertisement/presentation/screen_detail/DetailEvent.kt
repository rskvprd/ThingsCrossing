package com.app.thingscrossing.feature_advertisement.presentation.screen_detail

import com.app.thingscrossing.feature_account.domain.model.UserProfile

sealed interface DetailEvent {
    object ToggleMorePricesVisibility : DetailEvent
    class OnProfileCardClick(val profile: UserProfile) : DetailEvent
}

sealed interface DetailViewModelEvent {
    class Navigate(val route: String): DetailViewModelEvent
}