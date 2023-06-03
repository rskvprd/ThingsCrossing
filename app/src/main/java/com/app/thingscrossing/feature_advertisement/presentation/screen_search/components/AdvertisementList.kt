package com.app.thingscrossing.feature_advertisement.presentation.screen_search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Divider
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdvertisementList(
    advertisements: List<Advertisement>,
    modifier: Modifier = Modifier,
    navController: NavController,
    isLoading: Boolean,
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                val position = layoutCoordinates.unclippedBoundsInWindow()
                shimmerInstance.updateBounds(position)
            },
        content = {
            if (isLoading) {
                items(10) {index ->
                    LoadingAdvertisementItem(
                        shimmerInstance = shimmerInstance,
                        height = 200.dp
                    )
                }
            } else {
                items(advertisements.size) { index ->
                    AdvertisementItem(
                        advertisement = advertisements[index],
                        navController = navController,
                        shimmerInstance = shimmerInstance,
                    )
                }
            }
        })
}