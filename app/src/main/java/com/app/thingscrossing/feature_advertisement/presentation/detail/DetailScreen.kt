package com.app.thingscrossing.feature_advertisement.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Characteristic
import com.app.thingscrossing.feature_advertisement.presentation.detail.components.InformationBlock
import com.app.thingscrossing.feature_advertisement.presentation.detail.components.Price
import com.app.thingscrossing.feature_advertisement.presentation.search.components.AdvertisementPicture

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAdvertisementScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    if (uiState.isLoading) {
        CircularProgressIndicator()
        return
    }
    if (uiState.advertisement == null) {
        Text(stringResource(R.string.deleted_advertisement))
        return
    }

    Scaffold(

    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                AdvertisementPictures(imageUrls = uiState.advertisement.imageUrls)
                Spacer(Modifier.height(10.dp))
                Price(prices = uiState.advertisement.prices, onlyMain = false)
                Text(
                    text = uiState.advertisement.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Description(text = uiState.advertisement.description)
                Spacer(Modifier.height(10.dp))
                Characteristics(characteristics = uiState.advertisement.characteristics)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementPictures(imageUrls: List<String>) {
    ElevatedCard() {
        AdvertisementPicture(
            modifier = Modifier.size(300.dp),
            imageUrls = imageUrls
        )
    }
}

@Composable
fun Description(text: String) {
    if (text.isBlank()) return
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun Characteristics(
    characteristics: ArrayList<Characteristic>,
) {
    if (characteristics.isEmpty()) return
    InformationBlock(label = stringResource(id = R.string.characteristics)) {
        characteristics.map {
            Row(
            ) {
                Text(
                    text = "${it.name}: ".capitalize(Locale.current),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(text = it.value, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}