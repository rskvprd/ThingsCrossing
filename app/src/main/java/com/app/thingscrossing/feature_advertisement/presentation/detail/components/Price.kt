package com.app.thingscrossing.feature_advertisement.presentation.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Price

@Composable
fun Price(
    prices: List<Price>,
    isOtherPricesVisible: Boolean,
    onChangeOtherPricesVisibility: () -> Unit,
) {
    if (prices.isEmpty()) {
        return
    }

    val mainPrice = prices[0]

    Row() {
        Text(
            text = "${mainPrice.value} ${mainPrice.currency.symbol}",
            style = MaterialTheme.typography.headlineLarge
        )
        TextButton(onClick = { onChangeOtherPricesVisibility() }) {
            Text(text = stringResource(id = R.string.more))
            Icon(
                imageVector = if (!isOtherPricesVisible) Icons.Default.ExpandMore else Icons.Default.ExpandLess,
                contentDescription = null
            )
        }
    }


    AnimatedVisibility(visible = isOtherPricesVisible) {
        Spacer(modifier = Modifier.height(20.dp))
        Column() {
            for (index in 1 until prices.size) {
                val price = prices[index]
                val symbol = price.currency.symbol
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${price.value} $symbol",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8F)
                )
            }
        }
    }
}