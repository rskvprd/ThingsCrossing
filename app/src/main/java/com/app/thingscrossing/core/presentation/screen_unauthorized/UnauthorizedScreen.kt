package com.app.thingscrossing.core.presentation.screen_unauthorized

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.ui.theme.ThingsCrossingTheme

@Composable
fun UnauthorizedScreen(
    modifier: Modifier = Modifier,
    toLoginScreen: () -> Unit,
    @StringRes additionalTextId: Int,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            Button(
                onClick = { toLoginScreen() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = stringResource(id = R.string.sign_in_title))
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(20.dp),
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp).padding(bottom = 40.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(id = R.string.you_need_sign_in),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = stringResource(id = additionalTextId),
                textAlign = TextAlign.Center,
            )
        }
    }

}

@Preview
@Composable
fun UnauthorizedScreenPreview() {
    ThingsCrossingTheme() {
        Scaffold() { paddingValues ->
            UnauthorizedScreen(
                modifier = Modifier.padding(paddingValues),
                toLoginScreen = {},
                additionalTextId = R.string.sign_in_before_check_your_advertisements,
            )
        }
    }
}