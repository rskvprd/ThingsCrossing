package com.app.thingscrossing.core.presentation

import androidx.compose.runtime.Composable

@Composable
fun BaseScreen(
    content: @Composable () -> Unit
) {

    content()
}