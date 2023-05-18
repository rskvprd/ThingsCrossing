package com.app.thingscrossing.feature_advertisement.presentation.detail

import androidx.annotation.StringRes
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement

data class DetailState(
    val isLoading: Boolean = false,
    @StringRes val errorId: Int? = null,
    val advertisement: Advertisement = Advertisement.DEFAULT,
    val isOtherPricesVisible: Boolean = false,
)