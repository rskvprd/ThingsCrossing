package com.app.thingscrossing.core.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.thingscrossing.core.utils.BottomBarScreens
import com.app.thingscrossing.feature_account.presentation.AccountScreen
import com.app.thingscrossing.feature_advertisement.presentation.advertisements.SearchScreen
import com.app.thingscrossing.feature_home.presentation.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Search.route
    ) {
        composable(route = BottomBarScreens.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomBarScreens.Search.route) {
            SearchScreen(navController)
        }
        composable(route = BottomBarScreens.Account.route) {
            AccountScreen(navController)
        }
    }
}