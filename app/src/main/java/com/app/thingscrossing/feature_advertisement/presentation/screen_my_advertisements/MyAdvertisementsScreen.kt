package com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.ConfirmDialog
import com.app.thingscrossing.core.presentation.screen_unauthorized.UnauthorizedScreen
import com.app.thingscrossing.feature_account.navigation.AccountScreens
import com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements.components.MyAdvertisementList
import com.app.thingscrossing.feature_advertisement.presentation.screen_my_advertisements.components.NoAdvertisements
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAdvertisementsScreen(
    navController: NavHostController,
    onEvent: (MyAdvertisementsEvent) -> Unit,
    eventFlow: MutableSharedFlow<MyAdvertisementsViewModelEvent>,
    uiState: MyAdvertisementsState,
) {
    LaunchedEffect(key1 = null) {
        eventFlow.collectLatest { event ->
            when (event) {
                is MyAdvertisementsViewModelEvent.Navigate -> navController.navigate(event.route)
                is MyAdvertisementsViewModelEvent.ShowSnackbar -> {
                    uiState.snackbarHostState.showSnackbar("Error")
                }
            }
        }
    }

    if (uiState.isConfirmDeleteAdvertisementVisible) {
        ConfirmDialog(
            onDismiss = { onEvent(MyAdvertisementsEvent.HideAdvertisementDeleteDialog) },
            onConfirm = { onEvent(MyAdvertisementsEvent.DeleteAdvertisement) },
            infoTextId = R.string.delete_advertisement_warning,
            confirmButtonTextId = R.string.delete
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = uiState.snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.my_advertisements))
                }
            )
        }) { paddingValues ->
        if (!uiState.isLoading && !uiState.isAuthorized) {
            UnauthorizedScreen(
                modifier = Modifier.padding(paddingValues),
                toLoginScreen = { navController.navigate(AccountScreens.ROUTE) },
                additionalTextId = R.string.sign_in_before_check_your_advertisements
            )
        } else if (uiState.myAdvertisementList.isEmpty() && !uiState.isLoading) {
            NoAdvertisements(paddingValues = paddingValues) {
                onEvent(MyAdvertisementsEvent.AddAdvertisement)
            }
        } else {
            MyAdvertisementList(
                paddingValues = paddingValues,
                advertisementList = uiState.myAdvertisementList,
                onEditAdvertisement = { advertisement ->
                    onEvent(
                        MyAdvertisementsEvent.EditAdvertisement(
                            advertisement.id!!
                        )
                    )
                },
                onAdvertisementClick = { advertisement ->
                    onEvent(
                        MyAdvertisementsEvent.ToDetailAdvertisement(
                            advertisement.id!!
                        )
                    )
                },
                isLoading = uiState.isLoading,
                onAddAdvertisement = { onEvent(MyAdvertisementsEvent.AddAdvertisement) },
                onDeleteAdvertisement = { advertisement ->
                    onEvent(MyAdvertisementsEvent.ShowAdvertisementDeleteDialog(advertisement))
                }
            )
        }
    }
}

