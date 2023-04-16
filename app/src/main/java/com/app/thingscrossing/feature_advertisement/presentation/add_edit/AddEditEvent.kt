package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import android.net.Uri
import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.util.AddEditPrice

sealed interface AddEditEvent {
    /**Dismiss any error in application*/
    object DismissError: AddEditEvent

    /**Upload advertisement to backend*/
    object UploadAdvertisement: AddEditEvent

    /**Upload image to backend*/
    data class UploadImage(val uri: Uri): AddEditEvent

    /**On dismiss image*/
    object DropImage: AddEditEvent

    /**On change address in text field*/
    data class AddressChange(val address: String) : AddEditEvent

    /**On change title in text field*/
    data class TitleChange(val title: String) : AddEditEvent

    /**On change description in text field*/
    data class DescriptionChange(val description: String) : AddEditEvent

    /**Delete existing currency*/
    data class DeleteCurrency(val currency: Currency) : AddEditEvent

    /**Add new currency*/
    data class AddNewCurrency(val currency: Currency) : AddEditEvent

    /**Change price value*/
    data class ChangePrice(val price: AddEditPrice): AddEditEvent

    /**When user pick image from gallery and Android give me Uri*/
    data class PickImage(val uri: Uri) : AddEditEvent

    /**Press on add image button*/
    object AddImageClick : AddEditEvent
    object DismissAddImageDialog : AddEditEvent
}
