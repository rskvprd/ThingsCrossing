package com.app.thingscrossing.feature_advertisement.presentation.screen_search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.presentation.util.FilterOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onToggleFilterOption: (FilterOption) -> Unit,
    onFilter: () -> Unit,
    filterOptions: List<FilterOption>,
) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        ) {
            Text(text = "${stringResource(id = R.string.advertisement_type)}: ")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FilterOption.ALL.map { filterOption ->
                    val selected = filterOptions.contains(filterOption)
                    FilterChip(
                        selected = selected,
                        onClick = { onToggleFilterOption(filterOption) },
                        label = {
                            Text(
                                text = stringResource(
                                    id = filterOption.nameId
                                )
                            )
                        },
                        leadingIcon = {
                            Icon(filterOption.icon, null)
                        }
                    )
                }
            }
            Button(onClick = { onFilter() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.apply))
            }
        }

    }
}