package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.app.thingscrossing.feature_advertisement.domain.model.Price
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.AddButton
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.MyTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    navController: NavHostController,
    viewModel: AddEditViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
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
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.choose_currency),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    )
                    Currency.getAvailableCurrencies().forEach { currency ->
                        val selected =
                            state.advertisement.prices.map { it.currency }.contains(currency)
                        TextButton(onClick = {
                            if (!selected) {
                                viewModel.onEvent(AddEditEvent.AddNewCurrency(currency))
                            } else {
                                viewModel.onEvent(AddEditEvent.DeleteCurrency(currency))
                            }
                        }) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = stringResource(currency.name) + ", ${currency.symbol}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (selected) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
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
            // Title
            MyTextField(
                value = state.advertisement.title,
                onValueChange = {
                    viewModel.onEvent(
                        AddEditEvent.TitleChange(it)
                    )
                },
                singleLine = true,
                label = R.string.title,
                placeholder = R.string.title_placeholder
            )

            // Description
            MyTextField(
                value = state.advertisement.description,
                onValueChange = {
                    viewModel.onEvent(AddEditEvent.DescriptionChange(it))
                },
                label = R.string.description,
                placeholder = R.string.description_placeholder,
            )

            // Address
            MyTextField(
                value = state.advertisement.address,
                onValueChange = {
                    viewModel.onEvent(AddEditEvent.AddressChange(it))
                },
                label = R.string.address,
                placeholder = R.string.address_placeholder,
                scaffoldState = scaffoldState,
                scope = scope
            )

            // Prices
            Box {
                PriceBlock(
                    state = state,
                    scaffoldState = scaffoldState,
                    scope = scope
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceBlock(
    state: AddEditState,
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
) {
    val focusManager = LocalFocusManager.current
    val prices = state.advertisement.prices
    val mainPrice: Price = if (prices.isEmpty()) Price.DEFAULT else prices[0]
    Column() {
        if (prices.isEmpty()) {
            AddButton(onClick = {
                scope.launch {
                    focusManager.clearFocus()
                    scaffoldState.bottomSheetState.expand()
                }
            }, textId = R.string.set_price)
            return@Column
        }

        Column() {
            for (price in prices) {
                MyTextField(
                    value = mainPrice.value.toString(),
                    onValueChange = {},
                    label = R.string.price,
                    placeholder = R.string.price_placeholder,
                    keyboardType = KeyboardType.Number,
                    leadingIcon = {
                        Text(
                            text = price.currency.symbol
                        )
                    },
                )
            }

            TextButton(onClick = {
                scope.launch {
                    focusManager.clearFocus()
                    scaffoldState.bottomSheetState.expand()
                }
            }) {
                Text(text = stringResource(id = R.string.add_edit_currency))
            }
        }
    }

}