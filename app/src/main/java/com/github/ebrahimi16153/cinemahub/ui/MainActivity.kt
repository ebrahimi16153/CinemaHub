package com.github.ebrahimi16153.cinemahub.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.ebrahimi16153.cinemahub.data.repository.DetailsRepository
import com.github.ebrahimi16153.cinemahub.ui.navigation.MainScaffold
import com.github.ebrahimi16153.cinemahub.ui.theme.CinemaHubTheme
import com.github.ebrahimi16153.cinemahub.viewmodel.DetailsViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.DiscoverViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.HomeViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewMode: SearchViewModel by viewModels()
    private val discoverViewModel: DiscoverViewModel by viewModels()
    private val detailsViewModel: DetailsViewModel by viewModels()
    @Inject
    lateinit var detailsRepository: DetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            CinemaHub(
                navHostController = navHostController,
                homeViewModel = homeViewModel,
                searchViewModel = searchViewMode,
                discoverViewModel = discoverViewModel,
                detailsViewModel = detailsViewModel,
                detailsRepository = detailsRepository
            )
        }
    }
}

@Composable
fun CinemaHub(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    discoverViewModel: DiscoverViewModel,
    detailsViewModel: DetailsViewModel,
    detailsRepository: DetailsRepository
) {
    CinemaHubTheme {
        MainScaffold(
            navController = navHostController,
            homeViewModel = homeViewModel,
            searchViewModel = searchViewModel,
            discoverViewModel = discoverViewModel,
            detailsViewModel = detailsViewModel,
            detailsRepository = detailsRepository)
    }
}