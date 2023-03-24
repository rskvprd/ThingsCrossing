package com.app.thingscrossing.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy


fun NavController.hasRoute(route: String): Boolean {
    return this.currentBackStackEntry?.destination?.hierarchy?.any { it.route == route } == true
}

fun NavController.hasNotRoute(route: String): Boolean {
    return this.currentBackStackEntry?.destination?.hierarchy?.any { it.route == route } == false
}