package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement

sealed interface MyAdvertisementsEvent {
    object DismissError : MyAdvertisementsEvent
    object AddAdvertisement : MyAdvertisementsEvent
    class EditAdvertisement(val advertisementId: Int) : MyAdvertisementsEvent

    class ToDetailAdvertisement(val advertisementId: Int) : MyAdvertisementsEvent

    class ShowAdvertisementDeleteDialog(val advertisement: Advertisement) : MyAdvertisementsEvent
    object HideAdvertisementDeleteDialog : MyAdvertisementsEvent

    object DeleteAdvertisement : MyAdvertisementsEvent
}

sealed interface MyAdvertisementsViewModelEvent {
    class Navigate(val route: String) : MyAdvertisementsViewModelEvent
    class ShowSnackbar(messageId: Int) : MyAdvertisementsViewModelEvent
}