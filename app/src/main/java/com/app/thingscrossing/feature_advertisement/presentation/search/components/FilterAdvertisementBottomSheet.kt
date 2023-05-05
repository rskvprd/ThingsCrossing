package com.app.thingscrossing.feature_advertisement.presentation.search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FilterAdvertisementBottomSheet() {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Filter advertisement")
    }
}