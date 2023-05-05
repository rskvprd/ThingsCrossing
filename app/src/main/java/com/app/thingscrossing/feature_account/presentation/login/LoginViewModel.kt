package com.app.thingscrossing.feature_account.presentation.login

import androidx.lifecycle.ViewModel
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    accountUseCases: AccountUseCases,

): ViewModel() {
    fun onEvent(event: LoginEvent) {
    }
}