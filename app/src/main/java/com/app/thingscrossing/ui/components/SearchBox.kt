package com.app.thingscrossing.ui.components

import android.util.Log
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
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import com.app.thingscrossing.BottomBarScreens
import com.app.thingscrossing.R
import com.app.thingscrossing.ui.theme.Typography
import com.app.thingscrossing.viewmodels.SearchBoxViewModel

@Composable
fun SearchBox(navController: NavController) {
    val viewModel: SearchBoxViewModel = viewModel()
    val leadingIconSize: Dp = 26.dp
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .onFocusChanged {
                val searchRoute = BottomBarScreens.Search.route
                if (it.isFocused) {
                    Log.d("asdf", "Search box is focused")
                    if (navController.currentBackStackEntry?.destination?.hierarchy?.any { it.route == searchRoute } == false) {
                        navController.navigate(searchRoute)
                    }
                }
            }
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.5.dp, vertical = 0.dp),
        textStyle = Typography.bodyMedium,
        singleLine = true,
        value = viewModel.searchValue,
        shape = RoundedCornerShape(30),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),

        keyboardActions = KeyboardActions(
            onSearch = {
                viewModel.onSearch(this)
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
        onValueChange = { viewModel.onSearchValueChanged(it) },
        trailingIcon = {
            Row(
                Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically
            ) {
                TrailingIcon(imageVector = Icons.Default.FilterAlt,
                    contentDescription = stringResource(id = R.string.filter),
                    onClick = { viewModel.onFilterClick() })

                TrailingIcon(imageVector = Icons.Default.Sort,
                    contentDescription = stringResource(id = R.string.sort),
                    onClick = { viewModel.onSortClick() })

                if (viewModel.showEraseIcon) {
                    TrailingIcon(imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(id = R.string.erase),
                        onClick = { viewModel.onEraseButtonClick() })
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