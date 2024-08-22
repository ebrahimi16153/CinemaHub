package com.github.ebrahimi16153.cinemahub.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.ebrahimi16153.cinemahub.ui.navigation.MainScaffold
import com.github.ebrahimi16153.cinemahub.ui.theme.CinemaHubTheme
import com.github.ebrahimi16153.cinemahub.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewMode:HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            CinemaHub(navHostController = navHostController,homeViewMode)

            }
        }
}

@Composable
fun CinemaHub(navHostController: NavHostController,homeViewModel: HomeViewModel){
    CinemaHubTheme {
        MainScaffold(navController = navHostController,homeViewModel)
    }
}