package com.github.ebrahimi16153.cinemahub.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.ebrahimi16153.cinemahub.ui.componnet.MyNavigationBar
import com.github.ebrahimi16153.cinemahub.utils.Route
import com.github.ebrahimi16153.cinemahub.viewmodel.DetailsViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.DiscoverViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.HomeViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.SearchViewModel

@Composable
fun MainScaffold(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    discoverViewModel: DiscoverViewModel,
    detailsViewModel: DetailsViewModel
) {

    Scaffold(
        bottomBar = {
            //check current route to hide BottomNavigation
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry?.destination?.route
            if (currentRoute != null) {
                if (!currentRoute.contains(Route.Discover.name))
                    MyNavigationBar(navController = navController)
            }
        }

    ) { itPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(itPadding)
        ) {
            //content
            Navigation(
                navHostController = navController,
                homeViewModel = homeViewModel,
                searchViewModel = searchViewModel,
                discoverViewModel = discoverViewModel,
                detailsViewModel = detailsViewModel
            )

        }
    }

}