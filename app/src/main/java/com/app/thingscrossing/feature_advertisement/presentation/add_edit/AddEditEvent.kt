package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import android.net.Uri
import com.app.thingscrossing.feature_advertisement.domain.model.Currency

sealed interface AddEditEvent {
    data class UploadImage(val uri: Uri): AddEditEvent

    object DropImage: AddEditEvent

    data class AddressChange(val address: String) : AddEditEvent

    data class TitleChange(val title: String) : AddEditEvent

    data class DescriptionChange(val description: String) : AddEditEvent

    data class DeleteCurrency(val currency: Currency) : AddEditEvent

    data class AddNewCurrency(val currency: Currency) : AddEditEvent

    data class ChangePrice(val currency: Currency, val value: Float): AddEditEvent

    data class PickImage(val uri: Uri) : AddEditEvent
 }
