package com.app.thingscrossing.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.thingscrossing.ui.theme.ThingsCrossingTheme
import com.app.thingscrossing.R

@Composable
fun Dialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {},
        dismissButton = {},
        text = { content() },

        )
}

@Preview
@Composable
fun Dialog_Preview() {
    ThingsCrossingTheme {
        Dialog(
            onDismissRequest = {},
        ) {
            Image(
                painterResource(id = R.drawable.ic_launcher_foreground),
                null
            )
        }
    }
}