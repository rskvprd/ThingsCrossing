package com.app.thingscrossing.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_account.navigation.AccountScreens
import com.app.thingscrossing.feature_advertisement.navigation.AdvertisementScreen

sealed class BottomBarScreens(
    @StringRes val nameResource: Int,
    val route: String,
    val icon: ImageVector,
) {
    object Search : BottomBarScreens(
        nameResource = R.string.search,
        route = AdvertisementScreen.Search.route,
        icon = Icons.Default.Search
    )

    object Account : BottomBarScreens(
        nameResource = R.string.account,
        route = AccountScreens.ROUTE,
        icon = Icons.Default.Person
    )

    object ChatRooms : BottomBarScreens(
        nameResource = R.string.chat_rooms_screen_name,
        route = "chat-rooms-screen",
        icon = Icons.Default.Chat
    )

    object MyAdvertisements : BottomBarScreens(
        nameResource = R.string.advertisements,
        route = AdvertisementScreen.MyAdvertisements.route,
        icon = Icons.Default.PostAdd
    )

    companion object {
        val ALL_SCREENS = listOf(
            Search,
            MyAdvertisements,
            ChatRooms,
            Account,
        )
    }
}
