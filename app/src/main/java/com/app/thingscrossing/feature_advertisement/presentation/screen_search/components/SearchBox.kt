package com.app.thingscrossing.feature_advertisement.presentation.screen_search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.thingscrossing.R
import com.app.thingscrossing.ui.theme.SearchBoxStyle

@Composable
fun SearchBox(
    onSearch: (String) -> Unit,
    onSearchValueChanged: (String) -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit,
    isEraseIconVisible: Boolean,
    onEraseClick: () -> Unit,
    searchValue: String,
    paddingValues: PaddingValues,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(paddingValues)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .height(55.dp)
                .weight(8.5f),
            textStyle = SearchBoxStyle.copy(fontSize = 18.sp),
            singleLine = true,
            value = searchValue,
            shape = RoundedCornerShape(30),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search, keyboardType = KeyboardType.Text
            ),

            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchValue)
                },
            ),
            placeholder = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = stringResource(
                            id = R.string.search
                        ), modifier = Modifier
                            .padding(end = 10.dp)
                            .size(23.dp)
                    )
                    Text(
                        stringResource(id = R.string.search),
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp),
                        textAlign = TextAlign.Center,
                    )
                }
            },
            onValueChange = {
                onSearchValueChanged(it)
            },
            trailingIcon = {
                Row(
                    Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isEraseIconVisible) {
                        TrailingIcon(imageVector = Icons.Default.Cancel,
                            contentDescription = stringResource(id = R.string.erase),
                            onClick = {
                                onEraseClick()
                            })
                    }
                }
            },
        )
        Row(
            Modifier.weight(4f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            SideIconButton(
                onClick = { onFilterClick() },
                imageVector = Icons.Default.FilterAlt
            )
            Spacer(modifier = Modifier.width(10.dp))
            SideIconButton(
                onClick = { onSortClick() },
                imageVector = Icons.Default.Sort
            )
        }
    }
}


@Composable
fun TrailingIcon(imageVector: ImageVector, contentDescription: String, onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() }, modifier = Modifier
            .padding(horizontal = 15.dp)
            .size(25.dp)
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun SideIconButton(imageVector: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier.size(45.dp),
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            imageVector = imageVector,
            contentDescription = stringResource(id = R.string.sort_by)
        )
    }
}