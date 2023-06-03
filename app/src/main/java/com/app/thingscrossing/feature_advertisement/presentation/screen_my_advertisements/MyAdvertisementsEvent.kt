package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements

sealed interface MyAdvertisementsEvent {
    object DismissError : MyAdvertisementsEvent
    object AddAdvertisement : MyAdvertisementsEvent
    class EditAdvertisement(val advertisementId: Int) : MyAdvertisementsEvent
}

sealed interface MyAdvertisementsViewModelEvent {
    class Navigate(val route: String) : MyAdvertisementsViewModelEvent
}