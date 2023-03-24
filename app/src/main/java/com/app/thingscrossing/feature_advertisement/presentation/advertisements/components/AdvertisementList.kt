package com.app.thingscrossing.feature_advertisement.presentation.advertisements.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@Composable
fun AdvertisementList(
    advertisements: List<Advertisement>,
    modifier: Modifier = Modifier,
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
    LazyColumn(
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                val position = layoutCoordinates.unclippedBoundsInWindow()
                shimmerInstance.updateBounds(position)
            },
        content = {
            items(advertisements) { advertisement ->
                AdvertisementItem(
                    advertisement = advertisement,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        })
}