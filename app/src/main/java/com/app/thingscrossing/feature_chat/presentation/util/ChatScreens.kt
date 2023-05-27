package com.app.thingscrossing.feature_chat.presentation.util

sealed class ChatScreens (val route: String) {
    object ChatScreen: ChatScreens(route = "chat-screen")
    object RoomScreen: ChatScreens(route = "room-screen")
}
