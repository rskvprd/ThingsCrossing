package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Price
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.AddEditState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceBlock(
    scaffoldState: BottomSheetScaffoldState,
    uiState: AddEditState,
    scope: CoroutineScope,
) {
    val focusManager = LocalFocusManager.current
    val prices = uiState.advertisement.prices
    val mainPrice: Price = if (prices.isEmpty()) Price.DEFAULT else prices[0]
    Column {
        if (prices.isEmpty()) {
            AddButton(onClick = {
                scope.launch {
                    focusManager.clearFocus()
                    scaffoldState.bottomSheetState.expand()
                }
            }, textId = R.string.set_price)
            return@Column
        }

        Column {
            for (price in prices) {
                EditTextField(
                    value = mainPrice.value.toString(),
                    onValueChange = {},
                    label = R.string.price,
                    placeholder = R.string.price_placeholder,
                    keyboardType = KeyboardType.Number,
                    leadingIcon = {
                        Text(
                            text = price.currency.symbol
                        )
                    },
                )
            }

            TextButton(onClick = {
                scope.launch {
                    focusManager.clearFocus()
                    scaffoldState.bottomSheetState.expand()
                }
            }) {
                Text(text = stringResource(id = R.string.add_edit_currency))
            }
        }
    }
}