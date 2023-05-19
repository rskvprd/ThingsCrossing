package com.app.thingscrossing.feature_account.presentation.account

import androidx.annotation.StringRes
import com.app.thingscrossing.feature_account.domain.model.UserProfile

data class AccountState(
    val isLoading: Boolean = true,
    val authKey: String? = null,
    val haveAccount: Boolean = false,
    val currentUserProfile: UserProfile? = null,
    @StringRes val errorMessageId: Int? = null,
) {
    val isAuthenticated = authKey != null
}