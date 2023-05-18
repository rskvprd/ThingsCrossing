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
import com.app.thingscrossing.feature_advertisement.presentation.util.AdvertisementScreen
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
                AdvertisementScreen.DetailAdvertisementAdvertisementScreen.route +
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
                .fillMaxSize()
        ) {
            Column(
                Modifier.fillMaxSize()
            ) {
                AdvertisementPicture(
                    modifier = Modifier
                        .size(pictureSize)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp),
                    model = advertisement.images.map { it.url }.firstOrNull(),
                    shimmerInstance = shimmerInstance
                )

                advertisement.prices.firstOrNull()?.let {
                    Text(
                        text = "${it.value} ${it.currency.symbol}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Text(
                    text = advertisement.title,
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

