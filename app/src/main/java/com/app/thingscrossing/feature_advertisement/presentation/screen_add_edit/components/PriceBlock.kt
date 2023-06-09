package com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.util.AddEditPrice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceBlock(
    scaffoldState: BottomSheetScaffoldState,
    prices: List<AddEditPrice>,
    scope: CoroutineScope,
    onPriceChange: (price: AddEditPrice, value: String) -> Unit,
) {
    var isPricesVisible: Boolean by remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(44.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(MaterialTheme.colorScheme.primary.copy(.1f))
        .clickable {
            isPricesVisible = !isPricesVisible
        }
        .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.set_price))
        Icon(
            imageVector = if (isPricesVisible) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
            contentDescription = null
        )
    }

    AnimatedVisibility(visible = isPricesVisible) {
        val focusManager = LocalFocusManager.current
        Block(
            title = stringResource(id = R.string.price),
            description = stringResource(id = R.string.price_description)
        ) {
            if (prices.isEmpty()) {
                AddButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            focusManager.clearFocus()
                            scaffoldState.bottomSheetState.expand()
                        }
                    }, textId = R.string.set_price
                )
                return@Block
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                for (price in prices) {
                    EditTextField(
                        value = price.value,
                        onValueChange = {
                            onPriceChange(price, it)
                        },
                        label = R.string.price,
                        placeholder = R.string.price_placeholder,
                        keyboardType = KeyboardType.Number,
                    ) {
                        Text(
                            text = price.currency.symbol
                        )
                    }
                }

                TextButton(
                    onClick = {
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
}