package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.StringRes
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel

data class AddEditState(
    val isLoading: Boolean = false,
    val advertisement: Advertisement = Advertisement.DEFAULT,
    val images: List<ImageModel> = emptyList(),
    val bitmap: Bitmap? = null,
    val uri: Uri? = null,
    @StringRes val errorId: Int? = null,
)