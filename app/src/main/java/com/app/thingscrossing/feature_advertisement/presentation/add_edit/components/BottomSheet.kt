package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.AddEditState


@Composable
fun BottomSheet(
    uiState: AddEditState,
    onAddCurrency: (currency: Currency) -> Unit,
    onRemoveCurrency: (currency: Currency) -> Unit,
) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.choose_currency),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )
            Currency.getAvailableCurrencies().forEach { currency ->
                val selected =
                    uiState.advertisement.prices.map { it.currency }.contains(currency)
                TextButton(onClick = {
                    if (!selected) {
                        onAddCurrency(currency)
                    } else {
                        onRemoveCurrency(currency)
                    }
                }) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(currency.name) + ", ${currency.symbol}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selected) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

