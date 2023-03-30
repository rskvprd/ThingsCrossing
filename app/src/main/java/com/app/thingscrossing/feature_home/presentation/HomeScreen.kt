package com.app.thingscrossing.feature_home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BottomNavigationBar
import com.app.thingscrossing.feature_advertisement.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController
            )
        },
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                navController.navigate(Screen.AddEditScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_icon_cont_desc)
                )
                Text(text = stringResource(R.string.add_advertisement))
            }
        }
    }
}