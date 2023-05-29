package com.app.thingscrossing.feature_advertisement.presentation.screen_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BackTopAppBar
import com.app.thingscrossing.feature_advertisement.domain.model.Characteristic
import com.app.thingscrossing.feature_advertisement.presentation.components.PictureList
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.Block
import com.app.thingscrossing.feature_advertisement.presentation.screen_detail.components.InformationBlock
import com.app.thingscrossing.feature_advertisement.presentation.screen_detail.components.Price
import com.app.thingscrossing.feature_advertisement.presentation.screen_detail.components.ProfileCardSmall
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.NetworkErrorMessage
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailAdvertisementScreen(
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(key1 = null) {
        viewModel.eventFlow.collectLatest {event ->
            when (event) {
                is DetailViewModelEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Scaffold(topBar = {
        BackTopAppBar(
            navController = navController,
            title = stringResource(id = R.string.detail_screen_title)
        )
    }) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                    return@Scaffold
                }
            }

            if (uiState.errorId != null) {
                NetworkErrorMessage(messageId = uiState.errorId)
                return@Scaffold
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                PictureList(imageUrls = uiState.advertisement.images.map { it.url })

                Spacer(Modifier.height(10.dp))

                Price(prices = uiState.advertisement.prices,
                    isOtherPricesVisible = uiState.isOtherPricesVisible,
                    onChangeOtherPricesVisibility = { viewModel.onEvent(DetailEvent.ToggleMorePricesVisibility) })

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = uiState.advertisement.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = uiState.advertisement.address)

                Spacer(modifier = Modifier.height(20.dp))

                ProfileCardSmall(
                    userProfile = uiState.advertisement.userProfile!!, onMessageClick = {
                        viewModel.onEvent(DetailEvent.ToChat(profile = uiState.advertisement.userProfile))
                    }, modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Description(text = uiState.advertisement.description)

                Spacer(Modifier.height(10.dp))

                Characteristics(characteristics = uiState.advertisement.characteristics)

                Spacer(modifier = Modifier.height(10.dp))
            }
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