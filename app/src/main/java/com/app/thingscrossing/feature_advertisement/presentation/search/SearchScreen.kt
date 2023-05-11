package com.app.thingscrossing.feature_advertisement.presentation.search

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.presentation.search.components.AdvertisementList

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val state = viewModel.uiState
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

        if (state.isLoading) {
            CircularProgressIndicator()
            return
        }
        if (state.errorId != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                NetworkErrorMessage(
                    messageId = state.errorId
                )
                IconButton(
                    onClick = { viewModel.onEvent(SearchEvent.RefreshNetwork) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(
                            R.string.refresh_button_cont_desc
                        )
                    )
                }
            }
            return
        }
        AdvertisementList(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            advertisements = viewModel.uiState.advertisements,
            navController = navController
        )
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