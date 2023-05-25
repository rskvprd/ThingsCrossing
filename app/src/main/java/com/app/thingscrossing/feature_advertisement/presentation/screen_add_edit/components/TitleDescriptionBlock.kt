package com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.AddEditState

@Composable
fun TitleDescriptionBlock(
    uiState: AddEditState,
    onTitleChange: (value: String) -> Unit,
    onDescriptionChange: (value: String) -> Unit,

    ) {
    EditTextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.title,
        onValueChange = {
            onTitleChange(it)
        },
        label = R.string.title,
        placeholder = R.string.title_placeholder,
        singleLine = true
    )
    EditTextField(
        modifier = Modifier
            .defaultMinSize(minHeight = 100.dp)
            .fillMaxWidth(),
        value = uiState.description,
        onValueChange = {
            onDescriptionChange(it)
        },
        label = R.string.description,
        placeholder = R.string.description_placeholder,
    )
}