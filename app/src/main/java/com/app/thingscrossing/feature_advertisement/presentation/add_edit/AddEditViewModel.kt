package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
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
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_advertisement.domain.use_case.AdvertisementUseCases
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.util.AddEditPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class AddEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val advertisementUseCases: AdvertisementUseCases,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    var uiState by mutableStateOf(AddEditState())
        private set


    init {
        initAdvertisement()
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.TitleChange -> {
                uiState = uiState.copy(
                    title = event.title
                )
            }
            is AddEditEvent.AddressChange -> {
                uiState = uiState.copy(
                    address = event.address
                )
            }
            is AddEditEvent.DescriptionChange -> {
                uiState = uiState.copy(
                    description = event.description
                )
            }
            is AddEditEvent.AddNewCurrency -> {
                val prices = uiState.prices

                if (prices.map { it.currency }.contains(event.currency)) {
                    throw IllegalStateException("Duplicated currencies")
                }

                uiState = uiState.copy(
                    prices = prices.toMutableList().apply {
                        add(AddEditPrice(currency = event.currency, value = ""))
                    }
                )
            }
            is AddEditEvent.ChangePrice -> {
                if (!AddEditPrice.isValid(event.price)) return
                val prices = uiState.prices.toMutableList()
                for (i in prices.indices) {
                    if (prices[i].currency == event.price.currency) {
                        prices[i] = prices[i].copy(value = event.price.value)
                        break
                    }
                }
                uiState = uiState.copy(
                    prices = prices
                )
            }
            is AddEditEvent.DeleteCurrency -> {
                uiState = uiState.copy(
                    prices = uiState.prices
                        .filter { it.currency != event.currency }
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
                    uiState.uri ?: throw IllegalStateException("Uploading photo before it pick")
                ).onEach { resource ->
                    uiState = when (resource) {
                        is Resource.Error -> {
                            uiState.copy(
                                errorId = resource.messageId,
                                isLoading = false,
                            )
                        }
                        is Resource.Loading -> {
                            Log.d("zRRR", resource.progression.toString())
                            uiState.copy(
                                uploadingProgress = resource.progression?.toFloat(),
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            uiState.copy(
                                isLoading = false,
                                uploadingProgress = null,
                                images = uiState.images.toMutableList().apply {
                                    add(
                                        ImageModel(
                                            url = resource.data?.url ?: throw IllegalStateException(
                                                "Server doesn't return image url"
                                            )
                                        )
                                    )
                                }
                            )
                        }
                    }
                }.launchIn(viewModelScope)
                uiState = uiState.copy(
                    bitmap = null,
                    uri = null,
                )
            }

            is AddEditEvent.UploadAdvertisement -> {
                advertisementUseCases.addAdvertisement(
                    advertisement = Advertisement(
                        title = uiState.title,
                        description = uiState.description,
                        prices = uiState.prices.map { it.toPrice() },
                        address = uiState.address,
                        images = uiState.images,
                        characteristics = uiState.characteristics,
                        exchanges = uiState.exchanges,
                        categories = uiState.categories,
                    )
                )
            }
            AddEditEvent.DismissError -> {
                uiState = uiState.copy(
                    errorId = null
                )
            }
        }
    }

    @Suppress("DEPRECATION")
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
                            images = uiState.images
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}