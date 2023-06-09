package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun MyAdvertisementList(
    paddingValues: PaddingValues,
    advertisementList: List<Advertisement>,
    onEditAdvertisement: (Advertisement) -> Unit,
    onAdvertisementClick: (Advertisement) -> Unit,
    onDeleteAdvertisement: (Advertisement) -> Unit,
    onAddAdvertisement: () -> Unit,
    isLoading: Boolean,
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    Scaffold(
        Modifier.padding(paddingValues),
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { onAddAdvertisement() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text(text = stringResource(id = R.string.place))
            }
        }
    ) { padding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(20.dp),
            modifier = Modifier
                .padding(padding)
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
                        onAdvertisementClick = { onAdvertisementClick(advertisementList[index]) },
                        onEditAdvertisement = { onEditAdvertisement(advertisementList[index]) },
                        onDeleteAdvertisement = { onDeleteAdvertisement(advertisementList[index]) }
                    )
                }
            }

        }
    }

}