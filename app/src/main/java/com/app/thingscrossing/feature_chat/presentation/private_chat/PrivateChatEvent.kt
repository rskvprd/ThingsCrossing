package com.app.thingscrossing.feature_chat.presentation.private_chat

sealed interface ChatEvent {
    object DismissError: ChatEvent

    class EditMessageChange(val message: String): ChatEvent

    object SendMessage: ChatEvent
}

sealed interface ChatViewModelEvent {

}