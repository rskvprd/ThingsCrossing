package com.app.thingscrossing.feature_advertisement.presentation.screen_detail

import androidx.navigation.NavHostController
import com.app.thingscrossing.feature_account.domain.model.UserProfile

sealed interface DetailEvent {
    object ToggleMorePricesVisibility : DetailEvent
    class ToChat(val profile: UserProfile): DetailEvent

    object DismissError: DetailEvent
}

sealed interface DetailViewModelEvent {
    class Navigate(val route: String) : DetailViewModelEvent
}