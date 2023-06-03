package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.core.addBaseUrl
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.components.PictureItem
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun MyAdvertisementItem(
    advertisement: Advertisement,
    onEditAdvertisement: () -> Unit,
) {
    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PictureItem(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = advertisement.images.firstOrNull()?.url?.addBaseUrl()
                )
                Column {
                    val priceText = advertisement.prices.firstOrNull() ?: ""
                    Text(priceText.toString(), style = MaterialTheme.typography.headlineSmall)
                    Text(text = advertisement.title)
                }
            }
            IconButton(
                onClick = { onEditAdvertisement() },
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
        Divider(
            Modifier
                .width(260.dp)
                .align(Alignment.End)
        )
    }
}

@Composable
fun LoadingMyAdvertisementItem(
    shimmer: Shimmer
) {
    Card(
        modifier = Modifier
            .shimmer(shimmer)
            .height(100.dp)
            .fillMaxWidth()
    ) {
    }
}