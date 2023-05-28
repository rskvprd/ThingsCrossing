package com.app.thingscrossing.feature_advertisement.presentation.screen_search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.ButtonToggleGroup
import com.app.thingscrossing.core.presentation.components.ButtonToggleGroupItem
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementSortVariant

@Composable
fun SortBottomSheet(
    currentVariant: AdvertisementSortVariant,
    onChangeVariant: (AdvertisementSortVariant) -> Unit,
    isOrderAscending: Boolean,
    onChangeOrder: (Boolean) -> Unit,
    onApplySort: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.sort_by),
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 16.sp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        ButtonToggleGroup(items = AdvertisementSortVariant.ALL.map {
            val textId = when (it) {
                is AdvertisementSortVariant.Date -> R.string.sort_by_date
                is AdvertisementSortVariant.Title -> R.string.sort_by_title
                is AdvertisementSortVariant.Price -> R.string.sort_by_price
            }

            ButtonToggleGroupItem(
                innerText = stringResource(id = textId),
                onClick = { onChangeVariant(it) },
                selected = it == currentVariant
            )
        })

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.sort_order),
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 16.sp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        ButtonToggleGroup(
            items = listOf(
                ButtonToggleGroupItem(
                    innerText = stringResource(id = R.string.ascending),
                    onClick = {
                        onChangeOrder(true)
                    },
                    selected = isOrderAscending
                ),
                ButtonToggleGroupItem(
                    innerText = stringResource(id = R.string.descending),
                    onClick = {
                        onChangeOrder(false)
                    },
                    selected = !isOrderAscending
                )
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        ElevatedButton(onClick = { onApplySort() }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(id = R.string.make_sort),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}