package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import com.app.thingscrossing.feature_advertisement.domain.model.Currency

sealed interface AddEditEvent {
    data class AddressChange(val address: String) : AddEditEvent
    data class TitleChange(val title: String) : AddEditEvent
    data class DescriptionChange(val description: String) : AddEditEvent
    object ToggleCurrencyEdit : AddEditEvent
    object SetupPrice : AddEditEvent

    data class AddNewCurrency(val currency: Currency) : AddEditEvent
    data class ChangePrice(val currency: Currency, val value: Float): AddEditEvent
 }
