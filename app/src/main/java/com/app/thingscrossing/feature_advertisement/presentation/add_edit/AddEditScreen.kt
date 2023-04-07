package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    navController: NavHostController,
    viewModel: AddEditViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_icon_desc)
                )
            }
        },
        sheetContent = {
            BottomSheet(
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
        Column(
            Modifier
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
            Block(title = stringResource(R.string.image_title), description = stringResource(R.string.image_description)) {
                AddEditImagesBlock(
                    onPickImage = { uri -> uri?.let { viewModel.onEvent(AddEditEvent.PickImage(it)) } },
                    onConfirmImage = { uiState.uri?.let { viewModel.onEvent(AddEditEvent.PickImage(it)) } },
                    onDismissImage = { viewModel.onEvent(AddEditEvent.DropImage) },
                    uiState = uiState,
                    images = uiState.images.map { image ->
                        { AsyncImage(model = image.url, contentDescription = null) }
                    }
                )
            }



            Block(title = stringResource(id = R.string.title_title), description = stringResource(id = R.string.title_description)) {
                TitleDescriptionBlock(uiState = uiState, onTitleChange = {TODO()}, onDescriptionChange = {TODO()})
            }
            Block(title = stringResource(id = R.string.address), description = stringResource(id = R.string.address_description)) {
                EditTextField(
                    value = uiState.advertisement.address,
                    onValueChange = {
                        viewModel.onEvent(AddEditEvent.AddressChange(it))
                    },
                    label = R.string.address,
                    placeholder = R.string.address_placeholder,
                    scaffoldState = scaffoldState,
                    scope = scope
                )
            }
            Block(title = stringResource(id = R.string.price), description = stringResource(id = R.string.price_description)) {
                PriceBlock(
                    scaffoldState = scaffoldState,
                    uiState = uiState,
                    scope = scope,
                )
            }
        }
    }


}




