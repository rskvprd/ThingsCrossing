package com.app.thingscrossing.feature_account.presentation.account

import androidx.annotation.StringRes
import com.app.thingscrossing.feature_account.domain.model.User

data class AccountState(
    val isLoading: Boolean = true,
    val authKey: String? = null,
    val haveAccount: Boolean = false,
    val currentUser: User? = null,
    @StringRes val errorMessageId: Int? = null,
) {
    val isAuthenticated = authKey != null
}