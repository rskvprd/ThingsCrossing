package com.app.thingscrossing.feature_advertisement.presentation.search.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.app.thingscrossing.R
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


@Composable
fun AdvertisementPicture(
    modifier: Modifier,
    imageUrls: List<String>,
    shimmerInstance: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View),
) {
    var isLoading by remember { mutableStateOf(true) }
    Card(
        modifier = if (isLoading) modifier.shimmer(shimmerInstance) else modifier,

        ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            onSuccess = {
                isLoading = false
            },
            contentScale = ContentScale.Crop,
            model = imageUrls.firstOrNull(),
            contentDescription = stringResource(id = R.string.advertisement)
        )
    }
}