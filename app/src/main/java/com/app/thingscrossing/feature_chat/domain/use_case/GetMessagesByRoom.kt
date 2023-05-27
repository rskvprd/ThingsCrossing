package com.app.thingscrossing.feature_chat.domain.use_case

import android.content.Context
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.data.local.AUTH_KEY
import com.app.thingscrossing.feature_account.data.local.authDataStore
import com.app.thingscrossing.feature_account.data.local.toAuthKey
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import com.app.thingscrossing.feature_chat.domain.model.Message
import com.app.thingscrossing.feature_chat.domain.repository.ChatRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class GetMessagesByRoom(
    private val repository: ChatRepository,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(chatRoom: ChatRoom): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val authKey = context.authDataStore.data.firstOrNull()!![AUTH_KEY]
        if (authKey == null) {
            emit(Resource.Error(errorMessageId = R.string.no_auth_key))
            return@flow
        } else {
            emitAll(
                Resource.defaultHandleApiResource {
                    repository.getMessagesByRoom(
                        authKey = authKey.toAuthKey(),
                        chatRoom = chatRoom
                    )
                }
            )
        }
    }
}