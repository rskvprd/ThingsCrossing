package com.app.thingscrossing.feature_advertisement.presentation.search.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementItem(
    advertisement: Advertisement,
    navController: NavController,
) {
    val pictureSize = 200.dp
    Column(
        modifier = Modifier
            .width(pictureSize)
            .padding(5.dp)
    ) {
        ElevatedCard(
            onClick = {
                navController.navigate(Screen.DetailAdvertisementScreen.route +
                        "?advertisementId=${advertisement.id}")
            },
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 3.dp
            )
        ) {
            AdvertisementPictures(
                modifier = Modifier
                    .size(pictureSize)
                    .padding(5.dp),
                imageUrls = advertisement.imageUrls,
            )
            Text(
                text = advertisement.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}