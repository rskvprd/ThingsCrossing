package com.app.thingscrossing.feature_advertisement.presentation.detail_advertisement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.thingscrossing.feature_advertisement.presentation.search.components.AdvertisementPictures

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAdvertisementScreen(
    viewModel: DetailAdvertisementViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    if (uiState.isLoading) {
        CircularProgressIndicator()
        return
    }
    if (uiState.advertisement == null) {
        Text("Что-то пошло не так")
        return
    }

    Scaffold(topBar = {
        SmallTopAppBar(
            title = {
                Text(uiState.advertisement.title)
            })
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column() {
                AdvertisementPictures(
                    modifier = Modifier.size(300.dp),
                    imageUrls = uiState.advertisement.imageUrls
                )
                Text(text = uiState.advertisement.title, style = MaterialTheme.typography.titleMedium)
                Text(text = uiState.advertisement.description, style = MaterialTheme.typography.bodyMedium)
            }

        }
    }
}