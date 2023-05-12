package com.app.thingscrossing.feature_account.presentation.base

import com.app.thingscrossing.feature_account.domain.model.User

sealed interface BaseAccountEvent {
    object SignOut : BaseAccountEvent
    class SignIn(val user: User) : BaseAccountEvent

    class SignUp(val user: User) : BaseAccountEvent
    object ChangeHaveAccount : BaseAccountEvent

    object DismissError : BaseAccountEvent
}

sealed interface BaseAccountViewModelEvent {
    class Navigate(val route: String) : BaseAccountViewModelEvent
}