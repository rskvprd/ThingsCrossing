package com.app.thingscrossing.feature_account.presentation.profile

import android.net.Uri

sealed interface ProfileEvent {
    object DismissError : ProfileEvent

    /**When user pick image from gallery and Android give me Uri*/
    data class PickImage(val uri: Uri) : ProfileEvent

    /**Press on add image button*/
    object AddImageClick : ProfileEvent

    object DismissAddImageDialog : ProfileEvent

    /**On dismiss image*/
    object DropImage : ProfileEvent

    /**Upload image to backend*/
    data class UploadImage(val uri: Uri) : ProfileEvent

    object SignOut : ProfileEvent

    object DismissSignOutDialog : ProfileEvent

    object ShowConfirmSignOutDialog : ProfileEvent
}

sealed interface ProfileViewModelEvent {
    class Navigate(val route: String) : ProfileViewModelEvent
}