package com.app.thingscrossing.feature_account.presentation.registration.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.app.thingscrossing.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    passwordValue: String,
    onPasswordChange: (String) -> Unit,
    isPasswordValid: Boolean,
    onVisibilityChange: () -> Unit,
    isPasswordVisible: Boolean,
) {
    TextFieldWithValidation(
        value = passwordValue,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        onValueChange = { password ->
            onPasswordChange(password)
        },
        label = R.string.password_label,
        placeholder = R.string.password_placeholder,
        isValid = isPasswordValid,
        invalidText = stringResource(id = R.string.password_invalid),
        trailingIcon = {
            IconButton(onClick = { onVisibilityChange() }) {
                Icon(
                    if (isPasswordVisible) {
                        Icons.Default.VisibilityOff
                    } else {
                        Icons.Default.Visibility
                    },
                    stringResource(id = R.string.toggle_show_password_icon_description)
                )
            }
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}