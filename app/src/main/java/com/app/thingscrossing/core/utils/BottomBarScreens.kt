package com.app.thingscrossing.core.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.thingscrossing.R

sealed class BottomBarScreens(
    @StringRes val nameResource: Int,
    val route: String,
    val icon: ImageVector,
) {
    object Home : BottomBarScreens(
        nameResource = R.string.home,
        route = "home_screen",
        icon = Icons.Default.Home
    )

    object Search : BottomBarScreens(
        nameResource = R.string.search,
        route = "search_screen",
        icon = Icons.Default.Search
    )

    object Account : BottomBarScreens(
        nameResource = R.string.account,
        route = "account_screen",
        icon = Icons.Default.Person
    )
}
