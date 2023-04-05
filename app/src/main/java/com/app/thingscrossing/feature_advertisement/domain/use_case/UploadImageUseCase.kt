package com.app.thingscrossing.feature_advertisement.domain.use_case

import android.content.Context
import android.net.Uri
import android.util.Log
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.core.UploadStreamRequestBody
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class UploadImageUseCase(
    private val repository: AdvertisementRepository,
    private val context: Context,
) {
    operator fun invoke(
        uri: Uri,
    ): Flow<Resource<ImageModel>> = callbackFlow {
        val stream = context.contentResolver.openInputStream(uri)
        if (stream == null) {
            send(Resource.Error(R.string.unexpected_error))
            return@callbackFlow
        }
        val requestBody = UploadStreamRequestBody(
            mediaType = "image/*",
            inputStream = stream,
            onUploadProgress = {
                Log.d("MyActivity", "On upload progress $it")
                trySend(Resource.Loading(progression = it))
            })

        val filePart = MultipartBody.Part.createFormData(
            name = "url",
            filename = "test.jpg",
            body = requestBody
        )
        try {
            val result = repository.uploadImage(filePart)
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