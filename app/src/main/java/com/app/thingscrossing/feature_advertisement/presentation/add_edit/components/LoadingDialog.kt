package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDialog(
    progression: Float?,
) {
    AlertDialog(
        onDismissRequest = {},
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (progression == null) {
                CircularProgressIndicator()
            } else {
                CircularProgressIndicator(progress = progression)
            }
        }
    }
}