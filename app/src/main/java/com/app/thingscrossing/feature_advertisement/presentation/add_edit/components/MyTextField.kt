package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions(),
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val options = KeyboardOptions(
        KeyboardCapitalization.Sentences,
        keyboardType = keyboardType,
        imeAction = ImeAction.Next
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(id = label)
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = placeholder)
            )
        },
        singleLine = singleLine,
        keyboardOptions = options,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
    )
}