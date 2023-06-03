package com.app.thingscrossing.feature_advertisement.presentation.screen_search

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.thingscrossing.core.presentation.components.SearchBox
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ErrorDialog
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.components.AdvertisementList
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.components.FilterBottomSheet
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.components.SortBottomSheet
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    uiState: SearchState,
    onEvent: (SearchEvent) -> Unit,
    eventChannel: SharedFlow<SearchViewModelEvent>,
) {
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            when (uiState.currentBottomSheet) {
                BottomSheet.FilterBottomSheet -> {
                    FilterBottomSheet()
                }

                BottomSheet.SortBottomSheet -> {
                    SortBottomSheet(
                        isOrderAscending = uiState.isAscendingSort,
                        currentVariant = uiState.sortVariant,
                        onChangeOrder = { order -> onEvent(SearchEvent.ChangeSortOrder(order)) },
                        onChangeVariant = { variant -> onEvent(SearchEvent.ChangeSortVariant(variant)) },
                        onApplySort = { onEvent(SearchEvent.ApplyOrder) }
                    )
                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        LaunchedEffect(key1 = null) {
            eventChannel.collectLatest { event ->
                when (event) {
                    SearchViewModelEvent.ShowBottomSheet -> {
                        scaffoldState.bottomSheetState.expand()
                    }

                    SearchViewModelEvent.HideBottomSheet -> {
                        scaffoldState.bottomSheetState.partialExpand()
                    }

                    is SearchViewModelEvent.Navigate -> navController.navigate(event.route)
                }
            }
        }

        Scaffold(
            bottomBar = {
                BottomAppBar {
                    SearchBox(
                        onSearch = { searchValue ->
                            onEvent(SearchEvent.Search(searchValue))
                            focusManager.clearFocus()
                        },
                        onSearchValueChanged = { searchValue ->
                            onEvent(SearchEvent.SearchValueChanged(searchValue))
                        },
                        onSortClick = {
                            onEvent(SearchEvent.ToggleSortSection)
                            focusManager.clearFocus()
                        },
                        onFilterClick = {
                            onEvent(SearchEvent.ToggleFilterSection)
                            focusManager.clearFocus()
                        },
                        onEraseClick = {
                            onEvent(SearchEvent.EraseSearchBox)
                        },
                        isEraseIconVisible = uiState.isEraseIconVisible,
                        searchValue = uiState.searchValue,
                        paddingValues = PaddingValues(horizontal = 20.dp)
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.errorId != null) {
                    ErrorDialog(
                        onDismissError = { onEvent(SearchEvent.DismissError) },
                        errorMessageId = uiState.errorId
                    )
                }

                AdvertisementList(
                    modifier = Modifier
                        .fillMaxSize(),
                    advertisements = uiState.advertisements,
                    navController = navController,
                    isLoading = uiState.isLoading,
                )
            }
        }

    }
}