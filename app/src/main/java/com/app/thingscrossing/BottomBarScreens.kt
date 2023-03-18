package com.app.thingscrossing

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreens(val name: String, val route: String, val icon: ImageVector) {
    object Home : BottomBarScreens(
        // TODO: придумать как сделать локализацию
//        Resources.getSystem().getString(R.string.home),
        name = "Home",
        route = "home_screen",
        icon = Icons.Default.Home
    )

    object Search : BottomBarScreens(
//        name = Resources.getSystem().getString(R.string.search),
        name = "Search",
        route = "search_screen",
        icon = Icons.Default.Search
    )

    object Account : BottomBarScreens(
//        name = Resources.getSystem().getString(R.string.account),
        name = "Account",
        route = "account_screen",
        icon = Icons.Default.Person
    )
}
