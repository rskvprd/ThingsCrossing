package com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Exchange
import com.app.thingscrossing.ui.theme.ThingsCrossingTheme

@Composable
fun ExchangeBlock(
    newExchange: String,
    onNewExchangeChange: (String) -> Unit,
    onAddExchange: () -> Unit,
    modifier: Modifier = Modifier,
    exchangeList: List<Exchange>,
    onDeleteExchange: (Exchange) -> Unit,
) {
    var isExchangesVisible: Boolean by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primary.copy(.1f))
            .clickable {
                isExchangesVisible = !isExchangesVisible
            }
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.add_exchange))
        Icon(
            imageVector = if (isExchangesVisible) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
            contentDescription = null
        )
    }

    AnimatedVisibility(visible = isExchangesVisible) {
        Block(
            title = stringResource(id = R.string.exchange_title),
            description = stringResource(
                id = R.string.exchange_description,
            ),
            modifier = modifier,
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newExchange,
                    onValueChange = onNewExchangeChange,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions {
                        onAddExchange()
                    }
                )
                FilledTonalIconButton(
                    onClick = { onAddExchange() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
            ExchangeList(exchangeList = exchangeList, onDeleteExchange = { onDeleteExchange(it) })
        }
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExchangeList(
    exchangeList: List<Exchange>,
    onDeleteExchange: (Exchange) -> Unit,
    readOnly: Boolean = false,
) {
    LazyHorizontalStaggeredGrid(
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        rows = StaggeredGridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalItemSpacing = 4.dp
    ) {
        items(exchangeList.size) { index ->
            InputChip(
                selected = false,
                onClick = {},
                label = { Text(text = exchangeList[index].productName) },
                trailingIcon = if (!readOnly) {
                    {
                        IconButton(onClick = { onDeleteExchange(exchangeList[index]) }) {
                            Icon(
                                imageVector = Icons.Default.DeleteForever,
                                contentDescription = null
                            )
                        }
                    }
                } else null
            )
        }
    }
}


@Preview
@Composable
fun ExchangePreview() {
    ThingsCrossingTheme {
        Scaffold { padding ->
            ExchangeBlock(
                newExchange = "",
                onNewExchangeChange = {},
                onAddExchange = {},
                modifier = Modifier.padding(padding),
                exchangeList = listOf(
                    Exchange("Samsung Galaxy Note 2"),
                    Exchange("iPhone 14"),
                    Exchange("OnePlus 10"),
                    Exchange("Ручка для письма"),
                    Exchange("Fork"),
                    Exchange("Batman 5"),
                ),
                onDeleteExchange = {},
            )
        }
    }
}