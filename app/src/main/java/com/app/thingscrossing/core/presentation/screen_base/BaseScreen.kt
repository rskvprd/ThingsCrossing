package com.app.thingscrossing.core.presentation.screen_base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.app.thingscrossing.core.presentation.components.BottomNavigationBar

@Composable
fun BaseScreen(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            content()
        }
    }
}