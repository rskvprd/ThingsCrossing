package com.app.thingscrossing.feature_home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text(stringResource(id = R.string.home)) })
        },
        bottomBar = {
            BottomNavigationBar(
                navController
            )
        },
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
        ) {
            Text("HomeScreen")
        }
    }
}