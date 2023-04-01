package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    scaffoldState: BottomSheetScaffoldState? = null,
    scope: CoroutineScope? = null,
) {
    val options = KeyboardOptions(
        KeyboardCapitalization.Sentences,
        keyboardType = keyboardType,
        imeAction = ImeAction.Next
    )

    OutlinedTextField(
        modifier = Modifier
            .onFocusChanged {
                if (it.isFocused && scaffoldState?.bottomSheetState?.isVisible == true) {
                    scope?.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                }
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