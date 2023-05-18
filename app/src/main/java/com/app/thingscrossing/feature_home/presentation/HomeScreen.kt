package com.app.thingscrossing.feature_home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.components.Block
import com.app.thingscrossing.feature_advertisement.presentation.util.AdvertisementScreen

@Composable
fun HomeScreen(
    navController: NavHostController,
    isAuthenticated: Boolean,
) {
    Box(
        Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        {
            Block(
                title = stringResource(id = R.string.advertisement),
                description = stringResource(id = R.string.add_advertisement_description)
            ) {
                if (isAuthenticated) {
                    Button(onClick = {
                        navController.navigate(AdvertisementScreen.AddEditAdvertisementScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_icon_cont_desc)
                        )
                        Text(text = stringResource(R.string.add_advertisement))
                    }
                } else {
                    Text(text = stringResource(id = R.string.sign_in_before_create_advertisement))
                }

            }
        }
    }
}