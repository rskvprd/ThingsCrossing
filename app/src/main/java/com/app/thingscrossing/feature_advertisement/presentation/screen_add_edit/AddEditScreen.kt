package com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BackTopAppBar
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.*
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.util.AddEditPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    navController: NavHostController,
    viewModel: AddEditViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    if (uiState.advertisementUploaded) {
        AlertDialog(
            onDismissRequest = { navController.navigateUp() },
            confirmButton = {
                Button(onClick = { navController.navigateUp() }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            title = { Text(text = stringResource(id = R.string.congratulation)) },
            text = { Text(text = stringResource(id = R.string.advertisement_uploaded)) }
        )
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BackTopAppBar(
                navController = navController,
                title = stringResource(id = R.string.add_advertisement)
            )
        },
        sheetContent = {
            CurrencyBottomSheet(
                uiState = uiState,
                onAddCurrency = { currency -> viewModel.onEvent(AddEditEvent.AddNewCurrency(currency)) },
                onRemoveCurrency = { currency ->
                    viewModel.onEvent(
                        AddEditEvent.DeleteCurrency(
                            currency
                        )
                    )
                }
            )
        }
    ) { paddingValues ->
        if (uiState.errorId != null) {
            ErrorDialog(
                onDismissError = { viewModel.onEvent(AddEditEvent.DismissError) },
                uiState.errorId
            )
        }
        if (uiState.isLoading) {
            LoadingDialog(progression = uiState.uploadingProgress)
        }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Block(
                title = stringResource(id = R.string.title),
                description = stringResource(id = R.string.title_tip)
            ) {
                TitleDescriptionBlock(
                    uiState = uiState,
                    onTitleChange = { viewModel.onEvent(AddEditEvent.TitleChange(it)) },
                    onDescriptionChange = { viewModel.onEvent(AddEditEvent.DescriptionChange(it)) },
                )
            }
            Block(
                title = stringResource(R.string.image_title),
                description = stringResource(R.string.image_description)
            ) {
                AddEditImagesBlock(
                    onPickImage = { uri -> uri?.let { viewModel.onEvent(AddEditEvent.PickImage(it)) } },
                    onConfirmImage = {
                        uiState.currentImageUri?.let { uri ->
                            viewModel.onEvent(
                                AddEditEvent.UploadImage(uri)
                            )
                        }
                    },
                    onDismissImage = { viewModel.onEvent(AddEditEvent.DropImage) },
                    uiState = uiState,
                    images = uiState.images.map { image ->
                        { AsyncImage(model = image.url, contentDescription = null) }
                    },
                    onAddImageClick = {
                        viewModel.onEvent(AddEditEvent.AddImageClick)
                    },
                    dismissAddImageDialog = {
                        viewModel.onEvent(AddEditEvent.DismissAddImageDialog)
                    }
                )
            }
            Block(
                title = stringResource(id = R.string.address),
                description = stringResource(id = R.string.address_description)
            ) {
                EditTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.address,
                    onValueChange = { address ->
                        viewModel.onEvent(AddEditEvent.AddressChange(address))
                    },
                    label = R.string.address,
                    placeholder = R.string.address_placeholder
                )
            }
            Block(
                title = stringResource(id = R.string.price),
                description = stringResource(id = R.string.price_description)
            ) {
                PriceBlock(
                    scaffoldState = scaffoldState,
                    prices = uiState.prices,
                    scope = scope,
                    onPriceChange = { price, value ->
                        viewModel.onEvent(
                            AddEditEvent.ChangePrice(
                                AddEditPrice(
                                    currency = price.currency,
                                    value = value
                                )
                            )
                        )
                    }
                )
            }

            Button(
                onClick = {
                    viewModel.onEvent(AddEditEvent.UploadAdvertisement)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Text(text = stringResource(id = R.string.upload_advertisement))
            }
        }
    }
}




