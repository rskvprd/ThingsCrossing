package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import androidx.annotation.StringRes
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement

data class AddEditState(
    val isLoading: Boolean = false,
    val advertisement: Advertisement = Advertisement.DEFAULT,
    @StringRes val errorId: Int? = null,
)

