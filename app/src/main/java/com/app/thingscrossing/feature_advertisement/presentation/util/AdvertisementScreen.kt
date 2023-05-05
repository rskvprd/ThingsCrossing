package com.app.thingscrossing.feature_advertisement.presentation.util

sealed class AdvertisementScreen (val route: String) {
    object AddEditAdvertisementScreen: AdvertisementScreen("add-edit-advertisement-screen")
    object DetailAdvertisementAdvertisementScreen: AdvertisementScreen("detail-advertisement-screen")
}