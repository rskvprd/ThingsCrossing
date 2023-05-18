package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        value = uiState.title,
        onValueChange = {
            onTitleChange(it)
        },
        label = R.string.title,
        placeholder = R.string.title_placeholder,
        singleLine = true
    )
    EditTextField(
        modifier = Modifier.defaultMinSize(minHeight = 100.dp),
        value = uiState.description,
        onValueChange = {
            onDescriptionChange(it)
        },
        label = R.string.description,
        placeholder = R.string.description_placeholder,
    )
}