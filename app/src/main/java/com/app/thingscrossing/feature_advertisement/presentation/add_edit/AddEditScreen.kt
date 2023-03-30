package com.app.thingscrossing.feature_advertisement.presentation.add_edit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.app.thingscrossing.feature_advertisement.domain.model.Price
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.MyTextField

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddEditScreen(
    navController: NavHostController,
    viewModel: AddEditViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_icon_desc)
                )
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
            )

            // Prices
            Box {
                PriceBlock(
                    state = state, viewModel = viewModel
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyItem(currency: Currency) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "${currency.symbol} ${stringResource(id = currency.name)}")
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.delete_icon_cont_desc)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceBlock(
    state: AddEditState,
    viewModel: AddEditViewModel,
) {
    val prices = state.advertisement.prices
    val mainPrice: Price = if (prices.isEmpty()) Price.DEFAULT else prices[0]
    Column() {
        if (state.isSetupCurrency) {
            Card {
                Column {
                    Currency.getAvailableCurrencies().forEach {
                        TextButton(onClick = {
                            try {
                                viewModel.onEvent(AddEditEvent.AddNewCurrency(it))
                            } catch (ex: Exception) {

                            }
                        }) {
                            Text(text = stringResource(it.name))
                        }
                    }
                }
            }
//            return@Column
        }

        if (prices.size == 0 && !state.isCurrencyEditVisible) {
            Button(onClick = {
                viewModel.onEvent(AddEditEvent.SetupPrice)
            }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = stringResource(id = R.string.add_icon_cont_desc)
                )
                Text(stringResource(id = R.string.set_price))
            }
            return@Column
        }

        if (prices.size == 1 && !state.isCurrencyEditVisible) {
            Column() {
                MyTextField(value = mainPrice.value.toString(),
                    onValueChange = {},
                    label = R.string.price,
                    placeholder = R.string.price_placeholder,
                    keyboardType = KeyboardType.Number,
                    leadingIcon = { Text(text = mainPrice.currencyCode.symbol) })

                TextButton(onClick = { viewModel.onEvent(AddEditEvent.ToggleCurrencyEdit) }) {
                    Text(text = stringResource(id = R.string.add_edit_currency))
                }
            }
            return@Column
        }

        Column {
            for (price in prices) {
                CurrencyItem(currency = price.currencyCode)
            }
        }
        Button(onClick = {
            viewModel.onEvent(AddEditEvent.SetupPrice)
        }) {
            Icon(
                imageVector = Icons.Default.ControlPointDuplicate,
                contentDescription = stringResource(id = R.string.add_icon_cont_desc)
            )
            Text(stringResource(id = R.string.add_new_currency))
        }
        Column {
            for (price in prices) {
                MyTextField(
                    value = mainPrice.value.toString(),
                    onValueChange = {},
                    label = R.string.price,
                    placeholder = R.string.price_placeholder,
                    keyboardType = KeyboardType.Number,
                    leadingIcon = {
                        Text(
                            text = price.currencyCode.symbol
                        )
                    },
                )
            }
        }
    }

}