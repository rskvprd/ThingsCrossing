package com.app.thingscrossing.feature_advertisement.presentation.screen_search.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
fun PictureItem(
    modifier: Modifier,
    model: Any?,
    shimmerInstance: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View),
    contentDescription: String = stringResource(id = R.string.advertisement)
) {
    var isLoading by remember { mutableStateOf(true) }
    Card(
        modifier =
        if (isLoading) modifier.shimmer(shimmerInstance)
        else modifier,
    ) {
        if (model == null) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Default.NoPhotography,
                contentDescription = stringResource(id = R.string.no_photo_cont_desc)
            )
            isLoading = false
        } else {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                onSuccess = {
                    isLoading = false
                },
                contentScale = ContentScale.Crop,
                model = model,
                contentDescription = contentDescription
            )
        }
    }
}