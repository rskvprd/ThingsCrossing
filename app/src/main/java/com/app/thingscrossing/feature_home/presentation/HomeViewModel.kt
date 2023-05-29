package com.app.thingscrossing.feature_home.presentation

import androidx.lifecycle.ViewModel
import com.app.thingscrossing.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    val isSignedIn = authService.isAuthenticated
}