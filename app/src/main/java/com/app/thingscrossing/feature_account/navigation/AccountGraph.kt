package com.app.thingscrossing.feature_account.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.thingscrossing.feature_account.presentation.profile.ProfileScreen
import com.app.thingscrossing.feature_account.presentation.profile.ProfileViewModel
import com.app.thingscrossing.feature_account.presentation.sign_up.SignUpScreen
import com.app.thingscrossing.feature_account.presentation.sign_up.SignUpViewModel
import com.app.thingscrossing.feature_account.presentation.sign_in.SignInScreen
import com.app.thingscrossing.feature_account.presentation.sign_in.SignInViewModel

fun NavGraphBuilder.accountGraph(navController: NavHostController) {
    navigation(startDestination = AccountScreens.SignInScreen.route, route = AccountScreens.ROUTE) {
        composable(
            route = AccountScreens.ProfileScreen.route,
        ) {
            val profileViewModel: ProfileViewModel = hiltViewModel()

            ProfileScreen(
                navController = navController,
                viewModel = profileViewModel,
            )
        }
        composable(route = AccountScreens.SignInScreen.route) {
            val signInViewModel: SignInViewModel = hiltViewModel()
            SignInScreen(
                navController = navController,
                viewModel = signInViewModel,
            )
        }

        composable(route = AccountScreens.SignUpScreen.route) {
            val signUpViewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(navController = navController, viewModel = signUpViewModel)
        }
    }
}