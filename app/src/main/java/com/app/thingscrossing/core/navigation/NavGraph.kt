package com.app.thingscrossing.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.thingscrossing.feature_account.presentation.AccountScreen
import com.app.thingscrossing.feature_advertisement.presentation.detail_advertisement.DetailAdvertisementScreen
import com.app.thingscrossing.feature_advertisement.presentation.search.SearchScreen
import com.app.thingscrossing.feature_advertisement.util.Screen
import com.app.thingscrossing.feature_home.presentation.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
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
        composable(
            route = Screen.DetailAdvertisementScreen.route +
                    "?advertisementId={advertisementId}",
            arguments = listOf(
                navArgument(
                    name = "advertisementId",
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            DetailAdvertisementScreen()
        }
    }
}