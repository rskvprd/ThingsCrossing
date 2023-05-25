package com.app.thingscrossing.feature_advertisement.presentation.screen_search

import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
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
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            contentAlignment = Alignment.Center
        ) {


            if (uiState.isLoading) {
                CircularProgressIndicator()
                return@BottomSheetScaffold
            }
            if (uiState.errorId != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    NetworkErrorMessage(
                        messageId = uiState.errorId
                    )
                    IconButton(
                        onClick = { onEvent(SearchEvent.RefreshNetwork) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(
                                R.string.refresh_button_cont_desc
                            )
                        )
                    }
                }
                return@BottomSheetScaffold
            }
            AdvertisementList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                advertisements = uiState.advertisements,
                navController = navController
            )
        }
    }
}


@Composable
fun NetworkErrorMessage(@StringRes messageId: Int) {
    Text(
        textAlign = TextAlign.Center,
        text = stringResource(id = messageId),
        style = MaterialTheme.typography.headlineSmall,
        softWrap = true,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}