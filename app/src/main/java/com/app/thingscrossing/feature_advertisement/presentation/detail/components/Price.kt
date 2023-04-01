package com.app.thingscrossing.feature_advertisement.presentation.detail.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.feature_advertisement.domain.model.Price

@Composable
fun Price(
    prices: List<Price>,
    onlyMain: Boolean,
) {
    if (prices.isEmpty()) {
        return
    }
    Log.d("FFFF", prices.size.toString())
    Row {
        val mainPrice = prices[0]
        val mainSymbol = mainPrice.currency.symbol
        Text(
            text = "${mainPrice.value} $mainSymbol",
            style = MaterialTheme.typography.headlineLarge
        )
    }
    if (onlyMain) return
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