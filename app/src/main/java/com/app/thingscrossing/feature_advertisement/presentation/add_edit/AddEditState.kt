package com.app.thingscrossing.feature_advertisement.presentation.add_edit
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.presentation.search.ConnectionState

data class AddEditState(
    val connectionState: ConnectionState = ConnectionState.Ok,
    val advertisement: Advertisement = Advertisement.DEFAULT,
    val isCurrencyEditVisible: Boolean = false,
    val isSetupCurrency: Boolean = false,
)

