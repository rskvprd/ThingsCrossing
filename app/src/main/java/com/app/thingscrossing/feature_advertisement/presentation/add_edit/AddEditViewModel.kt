package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.core.navigation.advertisementIdNavArgument
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.Price
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val advertisementUseCases: AdvertisementUseCases,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private var advertisement: Advertisement? = null

    var uiState by mutableStateOf(AddEditState())
        private set


    init {
        initAdvertisement()
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.TitleChange -> {
                advertisement = advertisement?.copy(title = event.title)
            }
            is AddEditEvent.AddressChange -> {
                advertisement = advertisement?.copy(address = event.address)
            }
            is AddEditEvent.DescriptionChange -> {
                advertisement = advertisement?.copy(description = event.description)
            }
            is AddEditEvent.AddNewCurrency -> {
                val prices = uiState.advertisement.prices
                if (prices.map { it.currency }.contains(event.currency)) {
                    throw IllegalStateException("Duplicated currencies")
                }
                uiState = uiState.copy(
                    advertisement = uiState.advertisement.copy(
                        prices = arrayListOf(
                            *(uiState.advertisement.prices.toTypedArray()),
                            Price(value = 0.0, currency = event.currency)
                        )
                    )
                )
            }
            is AddEditEvent.ChangePrice -> TODO()
            is AddEditEvent.DeleteCurrency -> {
                uiState = uiState.copy(
                    advertisement = uiState.advertisement.copy(
                        prices = uiState.advertisement.prices
                            .filter { it.currency != event.currency } as ArrayList<Price>
                    )
                )
            }
            is AddEditEvent.PickImage -> {
                val bitmap = getBitmapByUri(event.uri)
                uiState = uiState.copy(
                    bitmap = bitmap,
                    uri = event.uri
                )
            }
            is AddEditEvent.DropImage -> {
                uiState = uiState.copy(
                    bitmap = null,
                    uri = null,
                )
            }
            is AddEditEvent.UploadImage -> {
                advertisementUseCases.uploadImageUseCase(
                    uiState.uri ?: throw IllegalStateException(
                        "Uploading photo before it pick"
                    )
                ).launchIn(viewModelScope)
                uiState = uiState.copy(
                    bitmap = null,
                    uri = null,
                )
            }
        }
    }

    private fun getBitmapByUri(uri: Uri): Bitmap {
        val bitmap: Bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, uri)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        return bitmap
    }

    private fun initAdvertisement() {
        val advertisementId = savedStateHandle.get<Int>(advertisementIdNavArgument)
        if (advertisementId != null && advertisementId > 0) {
            advertisementUseCases.getAdvertisement(advertisementId).onEach { result ->
                uiState = when (result) {
                    is Resource.Error -> {
                        AddEditState(
                            errorId = result.messageId ?: R.string.unexpected_error
                        )
                    }
                    is Resource.Loading -> {
                        AddEditState(isLoading = true)
                    }
                    is Resource.Success -> {
                        AddEditState(
                            advertisement = result.data ?: Advertisement.DEFAULT,
                            images = advertisement?.images ?: emptyList()
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}