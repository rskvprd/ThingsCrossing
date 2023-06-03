package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R

@Composable
fun NoAdvertisements(
    paddingValues: PaddingValues,
    onAddAdvertisement: () -> Unit
) {
    Box(
        Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.advertisement),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
            )

            Text(
                text = stringResource(R.string.you_have_no_advertisements),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.but_you_can_add_it),
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = { onAddAdvertisement() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 10.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Text(text = stringResource(id = R.string.add_advertisement))
        }
    }
}