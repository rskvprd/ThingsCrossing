package com.app.thingscrossing.feature_advertisement.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
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
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                val position = layoutCoordinates.unclippedBoundsInWindow()
                shimmerInstance.updateBounds(position)
            },
        content = {
            items(advertisements.size) { index ->
                AdvertisementItem(
                    advertisement = advertisements[index],
                    navController = navController
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        })
}