package com.app.thingscrossing.feature_account.presentation.registration.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.app.thingscrossing.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameField(
    usernameValue: String,
    onUsernameChange: (String) -> Unit,
    isUsernameValid: Boolean,
) {
    TextFieldWithValidation(
        value = usernameValue,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Ascii
        ),
        onValueChange = { username ->
            onUsernameChange(username)
        },
        label = R.string.login_label,
        placeholder = R.string.login_placeholder,
        isValid = isUsernameValid,
        invalidText = stringResource(id = R.string.username_invalid),
    )
}