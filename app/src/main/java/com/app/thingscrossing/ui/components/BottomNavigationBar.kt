package com.app.thingscrossing.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.thingscrossing.BottomBarScreens

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar(
        Modifier.height(140.dp),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 0.dp),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            Arrangement.SpaceEvenly, Alignment.CenterHorizontally,
        ) {
            SearchBox(navController)
            NavigationItems(navController)
        }
    }
}

@Composable
fun NavigationItems(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreens.Home,
        BottomBarScreens.Search,
        BottomBarScreens.Account
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            NavigationIconItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun NavigationIconItem(
    screen: BottomBarScreens,
    currentDestination: NavDestination?,
    navController: NavHostController,
    iconSize: Dp = 35.dp,
) {
    val selected: Boolean = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    IconButton(
        onClick = {
            if (!selected) {
                navController.popBackStack()
                navController.navigate(screen.route)
            }
        },
        enabled = !selected,
        colors = IconButtonDefaults.iconButtonColors(
            disabledContentColor = LocalContentColor.current
        ),
        modifier = Modifier.size(iconSize)
    ) {
        Icon(
            tint = if (selected) LocalContentColor.current else LocalContentColor.current.copy(alpha = 0.5F),
            imageVector = screen.icon,
            contentDescription = screen.name,
            modifier = Modifier.fillMaxSize(),
        )
    }
}