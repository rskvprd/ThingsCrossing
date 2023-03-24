package com.app.thingscrossing.ui.components

import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.navigation.BottomBarScreens
import com.app.thingscrossing.core.navigation.hasNotRoute
import com.app.thingscrossing.ui.theme.Typography

@Composable
fun SearchBox(
    navController: NavController,
    onSearch: (String) -> Unit,
    onSearchValueChanged: (String) -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit,
) {
    val leadingIconSize: Dp = 26.dp
    val focusManager = LocalFocusManager.current
    var searchValue by remember {
        mutableStateOf("")
    }
    var isEraseIconVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .onFocusChanged {
                if (it.isFocused) {
                    if (navController.hasNotRoute(BottomBarScreens.Search.route)) {
                        navController.popBackStack()
                        navController.navigate(BottomBarScreens.Search.route)
                    }
                }
            }
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.5.dp, vertical = 0.dp),
        textStyle = Typography.bodyMedium,
        singleLine = true,
        value = searchValue,
        shape = RoundedCornerShape(30),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),

        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchValue)
                focusManager.clearFocus()
            },
        ),
        placeholder = {
            Row {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = stringResource(
                        id = R.string.search
                    ), modifier = Modifier
                        .padding(end = 10.dp)
                        .size(leadingIconSize)
                )
                Text(
                    stringResource(id = R.string.search),
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
        },
        onValueChange = {
            if (it.isNotBlank()) {
                searchValue = it
                isEraseIconVisible = true
            } else {
                searchValue = ""
                isEraseIconVisible = false
            }
            searchValue = it
            onSearchValueChanged(it)
        },
        trailingIcon = {
            Row(
                Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically
            ) {
                TrailingIcon(imageVector = Icons.Default.FilterAlt,
                    contentDescription = stringResource(id = R.string.filter),
                    onClick = { onFilterClick() })

                TrailingIcon(imageVector = Icons.Default.Sort,
                    contentDescription = stringResource(id = R.string.sort),
                    onClick = { onSortClick() })

                if (isEraseIconVisible) {
                    TrailingIcon(imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(id = R.string.erase),
                        onClick = {
                            searchValue = ""
                            isEraseIconVisible = false
                        })
                }
            }
        },

        )
}


@Composable
fun TrailingIcon(imageVector: ImageVector, contentDescription: String, onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() }, modifier = Modifier
            .padding(horizontal = 15.dp)
            .size(26.dp)
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}