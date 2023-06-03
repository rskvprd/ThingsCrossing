package com.app.thingscrossing.feature_chat.navigation

sealed class ChatScreens (val route: String) {
    object ChatScreen: ChatScreens(route = "chat-screen")
    object RoomScreen: ChatScreens(route = "room-screen")

    companion object {
        const val ROUTE = "chat"
    }
}
