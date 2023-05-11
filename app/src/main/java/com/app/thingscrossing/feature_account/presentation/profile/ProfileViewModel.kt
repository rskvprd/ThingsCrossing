package com.app.thingscrossing.feature_account.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.thingscrossing.feature_account.domain.use_case.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases,
): ViewModel() {
    fun onEvent(event: ProfileEvent) {
    }
}