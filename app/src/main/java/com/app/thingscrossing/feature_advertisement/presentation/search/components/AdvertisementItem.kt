package com.app.thingscrossing.feature_advertisement.presentation.search.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.presentation.detail.components.Price
import com.app.thingscrossing.feature_advertisement.presentation.util.Screen
import com.valentinilk.shimmer.Shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementItem(
    advertisement: Advertisement,
    navController: NavController,
    pictureSize: Dp,
    shimmerInstance: Shimmer,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            navController.navigate(
                Screen.DetailAdvertisementScreen.route +
                        "?advertisementId=${advertisement.id}"
            )
        },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Box(
            Modifier
                .padding(10.dp)
                .fillMaxSize()) {
            Column(
                Modifier.fillMaxSize()
            ) {
                AdvertisementPicture(
                    modifier = Modifier
                        .size(pictureSize)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp),
                    model = advertisement.images.map{it.url}.firstOrNull(),
                    shimmerInstance = shimmerInstance
                )
                Price(prices = advertisement.prices, onlyMain = true)
                Text(
                    text = advertisement.title,
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

