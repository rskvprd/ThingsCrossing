package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import android.net.Uri
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.util.AddEditPrice

sealed interface AddEditEvent {
    object DismissError: AddEditEvent

    object UploadAdvertisement: AddEditEvent

    data class UploadImage(val uri: Uri): AddEditEvent

    object DropImage: AddEditEvent

    data class AddressChange(val address: String) : AddEditEvent

    data class TitleChange(val title: String) : AddEditEvent

    data class DescriptionChange(val description: String) : AddEditEvent

    data class DeleteCurrency(val currency: Currency) : AddEditEvent

    data class AddNewCurrency(val currency: Currency) : AddEditEvent

    data class ChangePrice(val price: AddEditPrice): AddEditEvent

    data class PickImage(val uri: Uri) : AddEditEvent
 }
