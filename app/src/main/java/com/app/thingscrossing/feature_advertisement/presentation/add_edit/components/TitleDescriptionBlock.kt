package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.AddEditState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleDescriptionBlock(
    uiState: AddEditState,
    onTitleChange: (value: String) -> Unit,
    onDescriptionChange: (value: String) -> Unit,

    ) {
    EditTextField(
        value = uiState.advertisement.title,
        onValueChange = {
            onTitleChange(it)
        },
        singleLine = true,
        label = R.string.title,
        placeholder = R.string.title_placeholder
    )
    EditTextField(
        value = uiState.advertisement.description,
        onValueChange = {
            onDescriptionChange(it)
        },
        label = R.string.description,
        placeholder = R.string.description_placeholder,
    )
}