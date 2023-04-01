package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = stringResource(id = R.string.add_icon_cont_desc)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(stringResource(id = textId))
    }
}