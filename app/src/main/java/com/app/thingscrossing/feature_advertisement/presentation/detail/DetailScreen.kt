package com.app.thingscrossing.feature_advertisement.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BackTopAppBar
import com.app.thingscrossing.feature_advertisement.domain.model.Characteristic
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.Block
import com.app.thingscrossing.feature_advertisement.presentation.detail.components.InformationBlock
import com.app.thingscrossing.feature_advertisement.presentation.detail.components.Price
import com.app.thingscrossing.feature_advertisement.presentation.detail.components.ProfileCardSmall
import com.app.thingscrossing.feature_advertisement.presentation.search.NetworkErrorMessage
import com.app.thingscrossing.feature_advertisement.presentation.search.components.AdvertisementPicture

@Composable
fun DetailAdvertisementScreen(
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            BackTopAppBar(
                navController = navController,
                title = stringResource(id = R.string.detail_screen_title)
            )
        }
    )
    { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
                return@Scaffold
            }

            if (uiState.errorId != null) {
                NetworkErrorMessage(messageId = uiState.errorId)
                return@Scaffold
            }
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                AdvertisementPictures(imageUrls = uiState.advertisement.images.map { it.url })

                Spacer(Modifier.height(10.dp))

                Price(
                    prices = uiState.advertisement.prices,
                    isOtherPricesVisible = uiState.isOtherPricesVisible,
                    onChangeOtherPricesVisibility = { viewModel.onEvent(DetailEvent.ToggleMorePricesVisibility) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Title
                Text(
                    text = uiState.advertisement.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = uiState.advertisement.address)

                Spacer(modifier = Modifier.height(20.dp))

                ProfileCardSmall(userProfile = uiState.advertisement.userProfile!!)

                Spacer(modifier = Modifier.height(20.dp))

                Description(text = uiState.advertisement.description)

                Spacer(Modifier.height(10.dp))

                Characteristics(characteristics = uiState.advertisement.characteristics)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdvertisementPictures(imageUrls: List<String>) {
    ElevatedCard {
        HorizontalPager(pageCount = imageUrls.size) {
            AdvertisementPicture(
                modifier = Modifier.size(300.dp), model = imageUrls[it]
            )
        }
    }
}

@Composable
fun Description(text: String) {
    if (text.isBlank()) return
    Block(title = stringResource(R.string.description), description = null, content = null)
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 20.dp),
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun Characteristics(
    characteristics: List<Characteristic>,
) {
    if (characteristics.isEmpty()) return
    InformationBlock(label = stringResource(id = R.string.characteristics)) {
        characteristics.map {
            Row {
                Text(
                    text = "${it.name}: ".capitalize(Locale.current),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(text = it.value, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}