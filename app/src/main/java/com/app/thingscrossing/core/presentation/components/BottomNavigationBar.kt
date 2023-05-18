package com.app.thingscrossing.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.thingscrossing.core.navigation.BottomBarScreens
import com.app.thingscrossing.feature_advertisement.presentation.search.SearchEvent
import com.app.thingscrossing.feature_advertisement.presentation.search.SearchViewModel

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    viewModel: SearchViewModel
) {
    val bottomBarScreenRoutes = BottomBarScreens.ALL_SCREENS.map { it.route }

    val focusManager = LocalFocusManager.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isSearchBoxVisible = navBackStackEntry?.destination?.route == BottomBarScreens.Search.route
    val isBottomBarScreen =
        bottomBarScreenRoutes.any { it == navBackStackEntry?.destination?.route }

    listOf("Search", "Main").any { it == navBackStackEntry?.destination?.route }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val contentPaddingValues = PaddingValues(horizontal = 30.dp)

        AnimatedVisibility(
            visible = isSearchBoxVisible,
            enter = slideInVertically(initialOffsetY = { it })
                    + fadeIn(),
            exit = slideOutVertically { it } + fadeOut()
        ) {
            BottomAppBar {
                SearchBox(
                    onSearch = { searchValue ->
                        viewModel.onEvent(SearchEvent.Search(searchValue))
                        focusManager.clearFocus()
                    },
                    onSearchValueChanged = { searchValue ->
                        viewModel.onEvent(SearchEvent.SearchValueChanged(searchValue))
                    },
                    onSortClick = {
                        viewModel.onEvent(SearchEvent.ToggleSortSection)
                        focusManager.clearFocus()
                    },
                    onFilterClick = {
                        viewModel.onEvent(SearchEvent.ToggleFilterSection)
                        focusManager.clearFocus()
                    },
                    onEraseClick = {
                        viewModel.onEvent(SearchEvent.EraseSearchBox)
                    },
                    isEraseIconVisible = viewModel.uiState.isEraseIconVisible,
                    searchValue = viewModel.uiState.searchValue,
                    paddingValues = contentPaddingValues
                )
            }

        }
        AnimatedVisibility(visible = isBottomBarScreen) {
            NavigationBar {
                NavigationItems(navController, contentPaddingValues)
            }
        }

    }
}

@Composable
fun NavigationItems(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarScreens.ALL_SCREENS.forEach { screen ->
            val selected: Boolean = currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true
            val focusManager = LocalFocusManager.current

            NavigationBarItem(
                label = { Text(text = stringResource(id = screen.nameResource)) },
                selected = selected,
                icon = { Icon(screen.icon, null) },
                onClick = {
                    if (!selected) {
                        navController.popBackStack()
                        navController.navigate(screen.route)
                    }
                    focusManager.clearFocus()
                }
            )
        }
    }

}