package com.app.thingscrossing.feature_advertisement.util

sealed class Screen (val route: String) {
    object DetailAdvertisementScreen: Screen("detail_advertisement_screen")
}
