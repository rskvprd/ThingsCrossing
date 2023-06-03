package com.app.thingscrossing.feature_advertisement.presentation.screen_search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.navigation.AdvertisementScreen
import com.valentinilk.shimmer.Shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementItem(
    advertisement: Advertisement,
    navController: NavController,
    shimmerInstance: Shimmer,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                navController.navigate(AdvertisementScreen.Detail(advertisementId = advertisement.id).route)
            },
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            PictureItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                model = advertisement.images.map { it.url }.firstOrNull(),
                shimmerInstance = shimmerInstance
            )

            Column(
                Modifier
                    .padding(10.dp)
                    .weight(1.2f)
            ) {
                Text(
                    text = advertisement.title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp)
                )

                advertisement.prices.firstOrNull().apply {
                    val text = if (this != null) "$value ${currency.symbol}" else ""
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = advertisement.address,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                    ),
                )
            }
            Divider()
        }
    }
}

