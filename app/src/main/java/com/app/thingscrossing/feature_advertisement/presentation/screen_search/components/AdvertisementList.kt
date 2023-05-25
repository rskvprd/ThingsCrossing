package com.app.thingscrossing.feature_advertisement.presentation.screen_search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@Composable
fun AdvertisementList(
    advertisements: List<Advertisement>,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
    val advertisementWidth: Dp = 180.dp
    LazyVerticalGrid(
        columns = GridCells.Adaptive(
            minSize = advertisementWidth
        ),
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                val position = layoutCoordinates.unclippedBoundsInWindow()
                shimmerInstance.updateBounds(position)
            },
        content = {
            items(advertisements.size) { index ->
                Box(Modifier.padding(10.dp)) {
                    AdvertisementItem(
                        advertisement = advertisements[index],
                        navController = navController,
                        pictureSize = advertisementWidth,
                        shimmerInstance = shimmerInstance,
                    )
                }
            }
        })
}