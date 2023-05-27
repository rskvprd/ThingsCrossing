package com.app.thingscrossing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.app.thingscrossing.core.navigation.NavGraph
import com.app.thingscrossing.core.presentation.BaseScreenWithBottomNavigation
import com.app.thingscrossing.feature_account.presentation.account.AccountViewModel
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.SearchViewModel
import com.app.thingscrossing.ui.theme.ThingsCrossingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val searchViewModel: SearchViewModel = hiltViewModel()

            ThingsCrossingTheme {
                BaseScreenWithBottomNavigation(
                    navController = navController,
                    searchViewModel = searchViewModel,
                ) {
                    NavGraph(
                        navController = navController,
                        searchViewModel = searchViewModel,
                    )
                }
            }
        }
    }
}