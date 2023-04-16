package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import android.net.Uri
import androidx.annotation.StringRes
import com.app.thingscrossing.feature_advertisement.domain.model.Category
import com.app.thingscrossing.feature_advertisement.domain.model.Characteristic
import com.app.thingscrossing.feature_advertisement.domain.model.Exchange
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.util.AddEditPrice


data class AddEditState(
    val title: String = "",
    val description: String = "",
    val address: String = "",
    val images: List<ImageModel> = emptyList(),
    val prices: List<AddEditPrice> = emptyList(),
    val characteristics: List<Characteristic> = emptyList(),
    val exchanges: List<Exchange> = emptyList(),
    val categories: List<Category> = emptyList(),
    val currentImageUri: Uri? = null,
    val isLoading: Boolean = false,
    val uploadingProgress: Float? = null,
    @StringRes val errorId: Int? = null,
    val showAddImageDialog: Boolean = false,
)