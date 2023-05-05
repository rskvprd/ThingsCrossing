package com.app.thingscrossing.feature_account.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.navigation.BottomBarScreens
import com.app.thingscrossing.feature_account.presentation.util.AccountScreen
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.Block

@Composable
fun LoginScreen(navController: NavController) {
    Block(title = stringResource(id = R.string.sign_in_title), description = stringResource(id = R.string.sign_in_description)) {
        Text(text = "LoginScreen")
        Column {
            TextButton(onClick = { navController.navigate(BottomBarScreens.Account.route) }) {
                Text(text = stringResource(id = R.string.does_not_have_account))
            }
        }
    }

}