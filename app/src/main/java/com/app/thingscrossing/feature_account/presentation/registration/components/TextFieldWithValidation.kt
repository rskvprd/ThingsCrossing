package com.app.thingscrossing.feature_account.presentation.registration.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithValidation(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    isValid: Boolean,
    invalidText: String,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        KeyboardCapitalization.Sentences,
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    leadingIcon: @Composable (() -> Unit)? = null,
    scaffoldState: BottomSheetScaffoldState? = null,
    scope: CoroutineScope? = null,
    visualTransformation: VisualTransformation? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
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
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        isError = !isValid,
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        trailingIcon = trailingIcon,
    )
    AnimatedVisibility(visible = !isValid) {
        Text(
            text = invalidText,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}