package com.app.thingscrossing.feature_advertisement.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun InformationBlock(
    label: String,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium
        )
        content()
    }
}