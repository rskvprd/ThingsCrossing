package com.app.thingscrossing.feature_advertisement.presentation.screen_search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.thingscrossing.R
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.8f),
            ) {
                PictureItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = advertisement.images.map { it.url }.firstOrNull(),
                    shimmerInstance = shimmerInstance
                )
                if (advertisement.exchanges.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.barter_icon),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 5.dp, end = 5.dp)
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White),
                    )
                }
            }


            Column(
                Modifier
                    .padding(10.dp)
                    .weight(1.2f)
            ) {
                Text(
                    text = advertisement.title,
                    style = MaterialTheme.typography.bodyLarge
                )

                ItemPrice(advertisement)

                Spacer(modifier = Modifier.height(10.dp))

            }
            Divider()
        }
        Text(
            text = advertisement.address,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(.7f)
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(15.dp)
        )
    }
}

@Composable
private fun ItemPrice(advertisement: Advertisement) {
    val prices = advertisement.prices
    val exchanges = advertisement.exchanges

    if (prices.isNotEmpty()) {
        prices.firstOrNull().apply {
            val text = if (this != null) "$value ${currency.symbol}" else ""

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
            )
        }
        return
    }

    if (exchanges.isNotEmpty()) return

    Text(
        text = stringResource(id = R.string.gift_advertisement_label),
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
    )
}
