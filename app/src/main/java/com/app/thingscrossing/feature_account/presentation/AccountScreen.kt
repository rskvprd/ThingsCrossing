package com.app.thingscrossing.feature_account.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(id = R.string.account)) })
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
        ) {
            Text("AccountScreen")
        }
    }
}