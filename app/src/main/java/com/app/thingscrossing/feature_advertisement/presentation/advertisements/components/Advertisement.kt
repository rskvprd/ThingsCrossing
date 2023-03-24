package com.app.thingscrossing.feature_advertisement.presentation.advertisements.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementItem(
    advertisement: Advertisement,
    modifier: Modifier = Modifier,
) {
    var isLoading by remember { mutableStateOf(true) }
    Column {
        ElevatedCard(
            modifier = if (isLoading) modifier.shimmer() else modifier,
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    onSuccess = {
                        isLoading = false
                    },
                    contentScale = ContentScale.Crop,
                    model = advertisement.imageUrls.firstOrNull(),
                    contentDescription = stringResource(id = R.string.advertisement)
                )
            }
        }
        Text(text = advertisement.title, style = MaterialTheme.typography.bodyMedium)
        Text(text = advertisement.description, style = MaterialTheme.typography.bodySmall)
    }

}