package com.app.thingscrossing.feature_advertisement.presentation.detail

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement

data class DetailState(
    val advertisement: Advertisement? = null,
    val isLoading: Boolean = false
)