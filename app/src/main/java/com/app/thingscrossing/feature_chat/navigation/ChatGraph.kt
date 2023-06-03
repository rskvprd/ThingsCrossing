package com.app.thingscrossing.feature_chat.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.app.thingscrossing.core.navigation.BottomBarScreens
import com.app.thingscrossing.feature_chat.presentation.private_chat.PrivateChatScreen
import com.app.thingscrossing.feature_chat.presentation.private_chat.PrivateChatViewModel
import com.app.thingscrossing.feature_chat.presentation.rooms.ChatRoomScreen
import com.app.thingscrossing.feature_chat.presentation.rooms.ChatRoomViewModel

fun NavGraphBuilder.chatGraph(navController: NavHostController) {
    navigation(startDestination = ChatScreens.RoomScreen.route, route = ChatScreens.ROUTE) {
        composable(route = BottomBarScreens.ChatRooms.route) {
            val chatRoomViewModel: ChatRoomViewModel = hiltViewModel()
            ChatRoomScreen(
                viewModel = chatRoomViewModel,
                navHostController = navController
            )
        }


        composable(
            route = ChatScreens.ChatScreen.route +
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
            val privateChatViewModel: PrivateChatViewModel = hiltViewModel()
            PrivateChatScreen(
                navController = navController,
                viewModel = privateChatViewModel,
            )
        }
    }

}