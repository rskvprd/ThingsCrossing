package com.app.thingscrossing.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.thingscrossing.feature_account.navigation.accountGraph
import com.app.thingscrossing.feature_advertisement.navigation.AdvertisementScreen
import com.app.thingscrossing.feature_advertisement.navigation.advertisementGraph
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.SearchViewModel
import com.app.thingscrossing.feature_chat.navigation.chatGraph


@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = AdvertisementScreen.ROUTE
    ) {
        chatGraph(navController = navController)
        advertisementGraph(navController = navController)
        accountGraph(navController = navController)
    }
}