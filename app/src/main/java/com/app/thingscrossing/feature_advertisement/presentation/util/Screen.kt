package com.app.thingscrossing.feature_advertisement.presentation.util

sealed class Screen (val route: String) {
    object AddEditScreen: Screen("add_edit_advertisement_screen")
    object DetailAdvertisementScreen: Screen("detail_advertisement_screen")
}
