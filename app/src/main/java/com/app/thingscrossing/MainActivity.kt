package com.app.thingscrossing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.app.thingscrossing.core.navigation.NavGraph
import com.app.thingscrossing.core.presentation.BaseScreenWithBottomNavigation
import com.app.thingscrossing.ui.theme.ThingsCrossingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ThingsCrossingTheme {
                BaseScreenWithBottomNavigation(
                    navController = navController,
                ) {
                    NavGraph(navController = navController)

                }
            }
        }
    }
}