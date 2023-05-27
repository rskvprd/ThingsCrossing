package com.app.thingscrossing.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.thingscrossing.feature_account.presentation.account.AccountScreen
import com.app.thingscrossing.feature_account.presentation.account.AccountViewModel
import com.app.thingscrossing.feature_account.presentation.util.AccountScreens
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.AddEditScreen
import com.app.thingscrossing.feature_advertisement.presentation.screen_detail.DetailAdvertisementScreen
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.SearchScreen
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.SearchViewModel
import com.app.thingscrossing.feature_advertisement.presentation.util.AdvertisementScreen
import com.app.thingscrossing.feature_chat.presentation.chat.ChatScreen
import com.app.thingscrossing.feature_chat.presentation.rooms.ChatRoomScreen
import com.app.thingscrossing.feature_chat.presentation.rooms.ChatRoomViewModel
import com.app.thingscrossing.feature_chat.presentation.util.ChatScreens
import com.app.thingscrossing.feature_home.presentation.HomeScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
) {
    val accountViewModel: AccountViewModel = hiltViewModel()
    val chatRoomViewModel: ChatRoomViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Search.route
    ) {
        composable(route = BottomBarScreens.Home.route) {
            HomeScreen(
                navController = navController,
                isAuthenticated = accountViewModel.uiState.isAuthenticated,
            )
        }

        composable(route = BottomBarScreens.Search.route) {
            SearchScreen(
                navController = navController,
                uiState = searchViewModel.uiState,
                onEvent = searchViewModel::onEvent,
                eventChannel = searchViewModel.eventChannel,
            )
        }

        composable(route = BottomBarScreens.Account.route) {
            AccountScreen(
                navController = navController,
                uiState = accountViewModel.uiState,
                onEvent = accountViewModel::onEvent
            )
        }

        composable(route = BottomBarScreens.ChatRooms.route) {
            ChatRoomScreen(
                currentUserProfile = accountViewModel.uiState.currentUserProfile,
                viewModel = chatRoomViewModel
            )
        }

        composable(
            route = AdvertisementScreen.DetailAdvertisementAdvertisementScreen.route +
                    "?advertisementId={advertisementId}",
            arguments = listOf(
                navArgument(
                    name = "advertisementId",
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            DetailAdvertisementScreen(
                navController,
            )
        }

        composable(route = ChatScreens.ChatScreen.route +
                "?userId={userId}&roomId={roomId}",
            arguments = listOf(
                navArgument(
                    name = "userId"
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = "roomId"
                ) {
                    type = NavType.IntType
                },
            )
        ) {
            ChatScreen(
                currentUserProfile = accountViewModel.uiState.currentUserProfile!!,
                navController = navController,
            )
        }

        composable(
            route = AdvertisementScreen.AddEditAdvertisementScreen.route +
                    "?advertisementId={advertisementId}",
            arguments = listOf(
                navArgument(
                    name = "advertisementId",
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditScreen(navController)
        }
    }
}