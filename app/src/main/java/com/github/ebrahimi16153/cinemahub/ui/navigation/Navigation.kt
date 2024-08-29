package com.github.ebrahimi16153.cinemahub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.ebrahimi16153.cinemahub.ui.screen.details.Details
import com.github.ebrahimi16153.cinemahub.ui.screen.discover.DiscoverScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.home.HomeScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.profile.ProfileScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.saved.SaveScreen
import com.github.ebrahimi16153.cinemahub.ui.screen.search.SearchScreen
import com.github.ebrahimi16153.cinemahub.utils.Route
import com.github.ebrahimi16153.cinemahub.viewmodel.DetailsViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.DiscoverViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.HomeViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.SearchViewModel

@Composable
fun Navigation(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    discoverViewModel: DiscoverViewModel,
    detailsViewModel: DetailsViewModel
) {

    ////////////////////////DISCOVER VALUES/////////////////////////////////////////////////////////

    /** ************************important note******************************************************
     *
     *  for Save and retain LazyColumn scroll position while using Paging 3
     *
     * 1>> we have to declare items as CollectAsLazyPagingItems() in before declaring your NavHost
     * 2>> for get items from ViewModel we have to use LaunchEffect and collectAsLazyPagingItems()
     *
     *
     * **/


    var genreId by remember { mutableStateOf("-1") }
    var genreName by remember { mutableStateOf("") }
    val isGrid by discoverViewModel.isGrid

    LaunchedEffect(key1 = genreId) {
        genreId.let { discoverViewModel.getMoviesByGenre(it) }
    }

    val discoverMovies = discoverViewModel.moviesByGenre.collectAsLazyPagingItems()
    val genres by discoverViewModel.genres.collectAsState()
////////////////////////////////////////////////////////////////////////////////////////////////////

    NavHost(navController = navHostController, startDestination = Route.Home.name) {

        composable(Route.Home.name) {
            HomeScreen(navHostController = navHostController, homeViewModel)
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


        composable(Route.Discover.name + "/{genreID}/{genreName}") { backStackEntry ->
          genreId = backStackEntry.arguments?.getString("genreID") ?: "-1"
            genreName = backStackEntry.arguments?.getString("genreName") ?: ""
            DiscoverScreen(
                discoverViewModel = discoverViewModel,
                navHostController = navHostController,
                genreName = genreName,
                genreID = genreId.toInt(),
                movies = discoverMovies,
                genres = genres,
                isGrid = isGrid,
                onGenreClick = { itGenre ->
                    genreId.let { discoverViewModel.getMoviesByGenre(itGenre.id.toString()) }

                },
                onGridClick = {
                    discoverViewModel.setIsGrid(!isGrid)
                })

        }


        composable(Route.Details.name + "/{movie_id}") { backStackEntry ->
            val movieID = backStackEntry.arguments?.getString("movie_id")?.toInt() ?: -1
            Details(navHostController = navHostController,detailsViewModel = detailsViewModel, movieID = movieID)

        }


    }

}