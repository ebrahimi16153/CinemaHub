package com.github.ebrahimi16153.cinemahub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.ebrahimi16153.cinemahub.ui.screen.details.Details
import com.github.ebrahimi16153.cinemahub.ui.screen.discover.DiscoverScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.home.HomeScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.profile.ProfileScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.saved.SaveScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.search.SearchScreen
import com.github.ebrahimi16153.cinemahub.utils.Route
import com.github.ebrahimi16153.cinemahub.viewmodel.DiscoverViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.HomeViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.SearchViewModel

@Composable
fun Navigation(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    discoverViewModel: DiscoverViewModel
) {

    NavHost(navController = navHostController, startDestination = Route.Home.name) {

        composable(Route.Home.name) {
            HomeScreen(navHostController = navHostController,homeViewModel)
        }

        composable(Route.Search.name) {
            SearchScreen(navHostController = navHostController, searchViewModel = searchViewModel)

        }

        composable(Route.Saved.name) {
            SaveScreen(navHostController = navHostController)

        }
        composable(Route.Profile.name) {
            ProfileScreen(navHostController = navHostController)

        }


        composable(Route.Discover.name+"/{genreID}/{genreName}"){backStackEntry ->
            val genreId = backStackEntry.arguments?.getString("genreID")?.toInt()?: -1
            val genreName = backStackEntry.arguments?.getString("genreName")?:""
            DiscoverScreen(navHostController = navHostController, genreName = genreName,genreID = genreId ,discoverViewModel = discoverViewModel)

        }


        composable(Route.Details.name+"/{movie_id}"){ backStackEntry ->
            val movieID = backStackEntry.arguments?.getString("movie_id")?.toInt()?: -1
            Details(movieID = movieID)

        }



    }

}