package com.app.thingscrossing.core.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopAppBar(
    navController: NavHostController,
    title: String,
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            BackButton(navController = navController)
        }
    )
}