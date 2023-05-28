package com.app.thingscrossing.feature_chat.data.remote

import com.app.thingscrossing.feature_chat.data.remote.request.GetOrCreatePrivateRoomRequest
import com.app.thingscrossing.feature_chat.data.remote.request.SendMessageRequest
import com.app.thingscrossing.feature_chat.domain.model.ChatRoom
import com.app.thingscrossing.feature_chat.domain.model.Message
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatApi {
    @POST("message/by-room/")
    suspend fun getMessages(
        @Header("Authorization") authKey: String,
        @Body chatRoomId: Int
    ): List<Message>

    @POST("message/")
    suspend fun sendMessage(@Header("Authorization") authKey: String, @Body request: SendMessageRequest): Message

    @POST("room/private/")
    suspend fun getOrCreatePrivateRoom(
        @Header("Authorization") authKey: String,
        @Body request: GetOrCreatePrivateRoomRequest
    ): ChatRoom

    @GET("room/")
    suspend fun getRooms(@Header("Authorization") authKey: String): List<ChatRoom>
}