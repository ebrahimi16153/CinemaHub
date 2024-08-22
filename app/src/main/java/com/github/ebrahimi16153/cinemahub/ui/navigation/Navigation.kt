package com.github.ebrahimi16153.cinemahub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.ebrahimi16153.cinemahub.ui.screen.home.HomeScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.profile.ProfileScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.saved.SaveScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.search.SearchScreen
import com.github.ebrahimi16153.cinemahub.utils.Route
import com.github.ebrahimi16153.cinemahub.viewmodel.HomeViewModel

@Composable
fun Navigation(navHostController: NavHostController, homeViewModel: HomeViewModel) {

    NavHost(navController = navHostController, startDestination = Route.Home.name) {

        composable(Route.Home.name) {
            HomeScreen(navHostController = navHostController,homeViewModel)
        }

        composable(Route.Search.name) {
            SearchScreen(navHostController = navHostController)

        }

        composable(Route.Saved.name) {
            SaveScreen(navHostController = navHostController)

        }
        composable(Route.Profile.name) {
            ProfileScreen(navHostController = navHostController)

        }

    }

}