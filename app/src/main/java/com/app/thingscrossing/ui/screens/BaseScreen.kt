package com.app.thingscrossing.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.thingscrossing.BottomNavGraph
import com.app.thingscrossing.ui.components.BottomNavigationBar
import com.app.thingscrossing.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen() {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) },
    ) { paddingValues ->
        val focusManager = LocalFocusManager.current
        Box(
            Modifier
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
        ) {
            BottomNavGraph(navController = navController)
        }
    }
}