package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.core.addBaseUrl
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.components.PictureItem
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAdvertisementItem(
    advertisement: Advertisement,
    onEditAdvertisement: () -> Unit,
    onAdvertisementClick: () -> Unit,
    onDeleteAdvertisement: () -> Unit,
) {
    val dismissState = rememberDismissState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                DismissValue.Default -> false
                DismissValue.DismissedToEnd -> false
                DismissValue.DismissedToStart -> {
                    onDeleteAdvertisement()
                    false
                }
            }
        },
        positionalThreshold = {
            it / 3f
        }
    )

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            SwipeBackground(dismissState = dismissState)
        },
        dismissContent = {
            AdvertisementCard(
                advertisement = advertisement,
                onEditAdvertisement = onEditAdvertisement,
                onAdvertisementClick = onAdvertisementClick
            )
        }
    )
}

@Composable
private fun AdvertisementCard(
    advertisement: Advertisement,
    onEditAdvertisement: () -> Unit,
    onAdvertisementClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(5.dp)
            .clickable {
                onAdvertisementClick()
            }
    ) {
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
                Column(
                ) {
                    val price = advertisement.prices.firstOrNull()
                    val firstPriceText: String? =
                        price?.let { "${price.value} ${price.currency.symbol}" }

                    if (firstPriceText.isNullOrBlank()) {
                        if (advertisement.exchanges.isNotEmpty()) {
                            Text(
                                stringResource(id = R.string.exchange),
                                style = MaterialTheme.typography.headlineSmall,
                            )
                        } else {
                            Text(
                                stringResource(id = R.string.gift_advertisement_label),
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    } else {
                        Text(firstPriceText, style = MaterialTheme.typography.headlineSmall)
                        if (advertisement.exchanges.isNotEmpty()) {
                            Text(
                                "+ ${stringResource(id = R.string.exchange)}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    Text(text = advertisement.title, modifier = Modifier.width(150.dp))
                }
            }
            FilledTonalIconButton(
                onClick = { onEditAdvertisement() },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .size(100.dp)
                    .padding(20.dp)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection ?: return

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> MaterialTheme.colorScheme.background
            DismissValue.DismissedToEnd -> Color.Green
            DismissValue.DismissedToStart -> MaterialTheme.colorScheme.error
        }
    )
    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }
    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Done
        DismissDirection.EndToStart -> Icons.Default.Delete
    }
    val iconTint: Color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> MaterialTheme.colorScheme.error
            DismissValue.DismissedToEnd -> Color.White
            DismissValue.DismissedToStart -> MaterialTheme.colorScheme.onError
        }
    )
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 1f else 1.5f
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.scale(scale),
            tint = iconTint
        )
    }
}

@Composable
fun LoadingMyAdvertisementItem(
    shimmer: Shimmer,
) {
    Card(
        modifier = Modifier
            .shimmer(shimmer)
            .height(100.dp)
            .fillMaxWidth()
    ) {
    }
}

@Preview
@Composable
fun MyAdvertisementItemPreview() {
    MyAdvertisementItem(
        advertisement = Advertisement.DEFAULT,
        onAdvertisementClick = {},
        onEditAdvertisement = {},
        onDeleteAdvertisement = {},

        )
}