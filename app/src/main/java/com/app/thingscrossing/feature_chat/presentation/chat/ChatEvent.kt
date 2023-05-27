package com.app.thingscrossing.feature_chat.presentation.chat

sealed interface ChatEvent {
    object DismissError: ChatEvent
}

sealed interface ChatViewModelEvent {

}