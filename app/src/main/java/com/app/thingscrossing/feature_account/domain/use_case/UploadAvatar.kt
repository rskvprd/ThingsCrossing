package com.app.thingscrossing.feature_account.domain.use_case

import android.content.Context
import android.net.Uri
import android.util.Log
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.core.UploadStreamRequestBody
import com.app.thingscrossing.feature_account.data.local.AUTH_KEY
import com.app.thingscrossing.feature_account.data.local.authDataStore
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.app.thingscrossing.feature_account.domain.repository.AccountRepository
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class UploadAvatar(
    private val repository: AccountRepository,
    private val context: Context,
) {
    operator fun invoke(
        userId: Int,
        avatarUri: Uri,
    ): Flow<Resource<UserProfile>> = callbackFlow {
        val authKey: String = context.authDataStore.data.first()[AUTH_KEY]
            ?: throw Exception("You have to sign in before change avatar")

        val stream = context.contentResolver.openInputStream(avatarUri)
        if (stream == null) {
            send(Resource.Error(R.string.unexpected_error))
            return@callbackFlow
        }
        val requestBody = UploadStreamRequestBody(
            mediaType = "image/*",
            inputStream = stream,
            onUploadProgress = {
                trySend(Resource.Loading(progression = it))
            })

        val filePart = MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "avatar.png",
            body = requestBody
        )

        try {
            send(Resource.Loading())
            val result =
                repository.uploadAvatar(authKey = authKey, userId = userId, avatar = filePart)
            send(Resource.Success(result))
        } catch (connectException: ConnectException) {
            send(Resource.Error(R.string.server_refused_message))
        } catch (e: SocketTimeoutException) {
            send(Resource.Error(R.string.server_timeout_message))
        } catch (e: HttpException) {
            send(Resource.Error(R.string.http_exception_message))
        }
        cancel()
    }
}