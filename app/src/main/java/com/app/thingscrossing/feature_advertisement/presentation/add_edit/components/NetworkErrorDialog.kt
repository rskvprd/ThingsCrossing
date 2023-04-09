package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.app.thingscrossing.R

@Composable
fun NetworkErrorDialog(
    onDismissError: () -> Unit,
    @StringRes errorMessageId: Int,
) {
    AlertDialog(
        onDismissRequest = { onDismissError() },
        dismissButton = { Button(onClick = { onDismissError() }) {
            Text(text = stringResource(id = R.string.ok))
        }},
        confirmButton = {
        },
        text = {
            Text(text = stringResource(id = errorMessageId))
        },
        title = {
            Text(text = stringResource(id = R.string.unexpected_error))
        }
    )
}