package com.app.thingscrossing.feature_advertisement.presentation.detail_advertisement

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement

data class DetailAdvertisementState(
    val advertisement: Advertisement? = null,
    val isLoading: Boolean = false
)