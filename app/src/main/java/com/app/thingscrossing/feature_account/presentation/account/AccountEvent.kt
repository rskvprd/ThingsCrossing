package com.app.thingscrossing.feature_account.presentation.account

import com.app.thingscrossing.feature_account.domain.model.User

sealed interface AccountEvent {
    object SignOut : AccountEvent
    class SignIn(val user: User) : AccountEvent

    class SignUp(val user: User) : AccountEvent
    object ChangeHaveAccount : AccountEvent

    object DismissError : AccountEvent
}

sealed interface AccountViewModelEvent {
    class Navigate(val route: String) : AccountViewModelEvent
}