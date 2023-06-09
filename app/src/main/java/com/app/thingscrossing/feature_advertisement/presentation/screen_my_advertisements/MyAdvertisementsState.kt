package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement

data class MyAdvertisementsState(
    @StringRes val errorMessageId: Int? = null,
    val isLoading: Boolean = false,
    val myAdvertisementList: List<Advertisement> = emptyList(),
    val isAuthorized: Boolean = false,
    val isConfirmDeleteAdvertisementVisible: Boolean = false,
    val currentDeletingAdvertisement: Advertisement? = null,
    val snackbarHostState: SnackbarHostState = SnackbarHostState()
)
