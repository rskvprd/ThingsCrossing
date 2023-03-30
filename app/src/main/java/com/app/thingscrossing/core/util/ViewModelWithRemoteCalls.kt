package com.app.thingscrossing.core.util

import com.app.thingscrossing.feature_advertisement.presentation.search.ConnectionState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException

interface ViewModelWithRemoteCalls {
    var connectionState: ConnectionState

    suspend fun <T> remoteRequest(request: suspend () -> T?): T? {
        connectionState = ConnectionState.Loading
        val response: Deferred<T?> = withContext(Dispatchers.IO) {
            async {
                try {
                    val okResponse = request()
                    connectionState = ConnectionState.Ok
                    okResponse
                } catch (connectException: ConnectException) {
                        connectionState = ConnectionState.RefusedConnection
                    return@async null
                } catch (ex: SocketTimeoutException) {
                        connectionState = ConnectionState.ServerTimeOut
                    return@async null
                }
            }
        }
        return response.await()
    }
}