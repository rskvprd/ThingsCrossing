package com.app.thingscrossing.feature_advertisement.presentation.screen_search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.thingscrossing.R
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


@Composable
fun PictureItem(
    modifier: Modifier = Modifier,
    model: Any?,
    shimmerInstance: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View),
    contentDescription: String = stringResource(id = R.string.advertisement)
) {
    var isLoading by remember { mutableStateOf(true) }
    Box(
        modifier =
        if (isLoading) modifier
            .shimmer(shimmerInstance)
        else modifier,
    ) {
        if (model == null) {
            Icon(
                modifier = modifier.fillMaxSize(),
                imageVector = Icons.Default.NoPhotography,
                contentDescription = stringResource(id = R.string.no_photo_cont_desc)
            )
            isLoading = false
        } else {
            AsyncImage(
                modifier = if (isLoading) modifier.shimmer(shimmerInstance)
                else modifier,
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