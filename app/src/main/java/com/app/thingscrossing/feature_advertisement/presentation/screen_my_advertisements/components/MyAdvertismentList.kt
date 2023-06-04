package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun MyAdvertisementList(
    paddingValues: PaddingValues,
    advertisementList: List<Advertisement>,
    onEditAdvertisement: (Advertisement) -> Unit,
    onAdvertisementClick: (Advertisement) -> Unit,
    isLoading: Boolean,
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(20.dp),
        modifier = Modifier
            .padding(paddingValues)
            .onGloballyPositioned { layoutCoordinates ->
                shimmer.updateBounds(layoutCoordinates.boundsInWindow())
            },
    ) {
        if (isLoading) {
            items(10) {
                LoadingMyAdvertisementItem(shimmer = shimmer)
            }
        } else {
            items(advertisementList.size) { index ->
                MyAdvertisementItem(
                    advertisementList[index],
                    onAdvertisementClick = {onAdvertisementClick(advertisementList[index])},
                    onEditAdvertisement = {onEditAdvertisement(advertisementList[index])}
                )
            }
        }

    }
}