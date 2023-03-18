package com.app.thingscrossing

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.thingscrossing.ui.screens.AccountScreen
import com.app.thingscrossing.ui.screens.HomeScreen
import com.app.thingscrossing.ui.screens.SearchScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Home.route
    ) {
        composable(route = BottomBarScreens.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreens.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarScreens.Account.route) {
            AccountScreen()
        }
    }
}