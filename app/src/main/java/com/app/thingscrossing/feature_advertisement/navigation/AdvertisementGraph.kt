package com.app.thingscrossing.feature_advertisement.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.AddEditScreen
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.AddEditViewModel
import com.app.thingscrossing.feature_advertisement.presentation.screen_detail.DetailAdvertisementScreen
import com.app.thingscrossing.feature_advertisement.presentation.screen_detail.DetailAdvertisementViewModel
import com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements.MyAdvertisementsScreen
import com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements.MyAdvertisementsViewModel
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.SearchScreen
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.SearchViewModel

fun NavGraphBuilder.advertisementGraph(navController: NavHostController) {
    navigation(
        startDestination = AdvertisementScreen.Search.route,
        route = AdvertisementScreen.ROUTE
    ) {
        composable(
            route = AdvertisementScreen.MyAdvertisements.route,
        ) {
            val myAdvertisementViewModel: MyAdvertisementsViewModel = hiltViewModel()

            MyAdvertisementsScreen(
                navController = navController,
                onEvent = myAdvertisementViewModel::onEvent,
                uiState = myAdvertisementViewModel.uiState,
                eventFlow = myAdvertisementViewModel.eventFlow,
            )
        }

        composable(
            route = with(AdvertisementScreen.Detail) {"$DEFAULT_ROUTE?$ID_KEY={$ID_KEY}"},
            arguments = listOf(
                navArgument(
                    name = AdvertisementScreen.Detail.ID_KEY,
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            val detailAdvertisementViewModel: DetailAdvertisementViewModel = hiltViewModel()
            DetailAdvertisementScreen(
                navController = navController,
                viewModel = detailAdvertisementViewModel
            )
        }

        composable(route = AdvertisementScreen.Search.route) {
            val searchViewModel: SearchViewModel = hiltViewModel()

            SearchScreen(
                navController = navController,
                uiState = searchViewModel.uiState,
                onEvent = searchViewModel::onEvent,
                eventChannel = searchViewModel.eventChannel
            )
        }

        composable(
            route = with(AdvertisementScreen.AddEdit) { "$DEFAULT_ROUTE?$ID_KEY={$ID_KEY}" },
            arguments = listOf(
                navArgument(name = AdvertisementScreen.AddEdit.ID_KEY) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val addEditViewModel: AddEditViewModel = hiltViewModel()

            AddEditScreen(
                navController = navController,
                uiState = addEditViewModel.uiState,
                onEvent = addEditViewModel::onEvent,
                eventFlow = addEditViewModel.eventFlow
            )
        }
    }
}