package com.app.thingscrossing.feature_advertisement.presentation.search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R

@Composable
fun FilterBottomSheet() {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.filter),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(text = "Filter advertisement variants")
        }

    }
}