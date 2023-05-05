package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Block(
    title: String,
    description: String,
    content: @Composable () -> Unit,
) {
    Spacer(modifier = Modifier.height(20.dp))
    Header(title = title, description = description)
    Spacer(modifier = Modifier.height(20.dp))
    content()
}

@Composable
fun Header(
    title: String,
    description: String,
) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Divider()
        Text(
            text = description,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
        )
        MaterialTheme.colorScheme.surface
    }

}