package com.app.thingscrossing.feature_advertisement.presentation.advertisements

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BottomNavigationBar
import com.app.thingscrossing.feature_advertisement.presentation.advertisements.components.AdvertisementList
import com.app.thingscrossing.ui.components.SearchBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text(stringResource(id = R.string.search)) })
        },
        bottomBar = {
            BottomNavigationBar(navController, Modifier.height(160.dp)) {
                SearchBox(
                    navController,
                    onSearch = { searchValue ->
                        viewModel.onEvent(SearchEvent.Search(searchValue))
                    },
                    onSearchValueChanged = {},
                    onSortClick = {},
                    onFilterClick = {},
                )
            }
        },
    ) { paddingValues ->
        val focusManager = LocalFocusManager.current
        Box(
            Modifier
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
        ) {
            AdvertisementList(
                advertisements = viewModel.uiState.advertisements,
            )
        }
    }
}