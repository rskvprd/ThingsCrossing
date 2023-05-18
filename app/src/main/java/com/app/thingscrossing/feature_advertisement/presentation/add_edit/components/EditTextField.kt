package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(
    modifier: Modifier = Modifier,
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
        modifier = modifier
            .onFocusChanged {
//                if (it.isFocused && scaffoldState?.bottomSheetState?.isVisible == true) {
//                    scope?.launch {
//                        scaffoldState.bottomSheetState.hide()
//                    }
//                }
            },
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