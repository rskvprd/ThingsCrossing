package com.app.thingscrossing.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.app.thingscrossing.R


@Composable
fun TopBar() {
    SmallTopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) }
    )
}
