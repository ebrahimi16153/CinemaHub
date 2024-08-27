package com.github.ebrahimi16153.cinemahub.ui.screen.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.ebrahimi16153.cinemahub.R
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.ui.componnet.GenreItem
import com.github.ebrahimi16153.cinemahub.ui.componnet.GridMovieItems
import com.github.ebrahimi16153.cinemahub.ui.componnet.RowMovieItems
import com.github.ebrahimi16153.cinemahub.utils.MyExtension.toGenre
import com.github.ebrahimi16153.cinemahub.utils.Route
import com.github.ebrahimi16153.cinemahub.viewmodel.DiscoverViewModel

@Composable
fun DiscoverScreen(
    navHostController: NavHostController,
    genreID: Int,
    discoverViewModel: DiscoverViewModel,
    genreName: String
) {


    val genres by discoverViewModel.genres.collectAsState()
    var currentGenreID by remember { mutableIntStateOf(genreID) }
    var currentGenreName by remember { mutableStateOf(genreName) }
    var isGrid by remember{ mutableStateOf(true) }

    //GET movies
    discoverViewModel.getMoviesByGenre(currentGenreID.toString())


    Column(modifier = Modifier.fillMaxWidth()) {
        //navigation and genres
        Column(modifier = Modifier
            .fillMaxSize()
            .height(56.dp), verticalArrangement = Arrangement.Top) {
             Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
               Row(verticalAlignment = Alignment.CenterVertically) {
                   IconButton(onClick = { navHostController.popBackStack()}) {
                       Icon(
                           imageVector = Icons.Rounded.ArrowBackIosNew,
                           contentDescription = stringResource(R.string.back_navigation))
                   }
                   Text(text = currentGenreName)
                   Spacer(modifier = Modifier.height(10.dp))
               }
                 IconButton(onClick = { isGrid = !isGrid }) {
                     Icon(imageVector = Icons.Rounded.Apps, contentDescription = "")
                 }
             }

            // genreList


            if (genreID != -1) {
                GenresList(
                    currentGenre = currentGenreID,
                    genres = genres,
                    onGenreClick = { itGenre ->
                        currentGenreID = itGenre.id
                        currentGenreName = itGenre.name.toString()
                        discoverViewModel.getMoviesByGenre(itGenre.id.toString())
                    })
            }

            //MovieList
            if (isGrid){

                DiscoverGridMovieList(discoverViewModel,onMovieClick = {itMovieId ->
                    navHostController.navigate(Route.Details.name+"/$itMovieId")
                })
            }else{
                
                DiscoverRowMovieList(discoverViewModel = discoverViewModel, onMovieClick = { itMovieId ->
                    navHostController.navigate(Route.Details.name+"/$itMovieId")
                
            })

            }


        }
    }
}


@Composable
fun GenresList(currentGenre: Int, genres: List<Genre>, onGenreClick: (Genre) -> Unit) {

    // state is a way to show FirstVisibleItem by index
    // so first find index of item and pass it to rememberLazyListState like blow code:

    val indexed = genres.indexOf(currentGenre.toGenre(genres))
    val state = rememberLazyListState(initialFirstVisibleItemIndex = indexed)
    LazyRow(
        state = state,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between items
    ) {
        itemsIndexed(genres, key = { _, movie -> movie.id }) { _, genre ->
            GenreItem(
                isSelected = currentGenre == genre.id,
                genre = genre,
                onGenreClick = { onGenreClick(it) })
        }
    }

}

@Composable
fun DiscoverGridMovieList(discoverViewModel: DiscoverViewModel, onMovieClick:(Int) -> Unit = {}){

    val movies = discoverViewModel.moviesByGenre.collectAsLazyPagingItems()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(130.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalItemSpacing = 3.dp,
        horizontalArrangement = Arrangement.spacedBy(1.dp)

    ) {

        items(count = movies.itemCount){ itIndex ->

            movies[itIndex]?.let {
                GridMovieItems(movie = it){ movieID ->
                    onMovieClick(movieID)
                }
            }
        }
    }


}


@Composable
fun DiscoverRowMovieList(discoverViewModel: DiscoverViewModel, onMovieClick:(Int) -> Unit = {}){

    val movies = discoverViewModel.moviesByGenre.collectAsLazyPagingItems()

   LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {


        items(count = movies.itemCount){ itIndex ->

            movies[itIndex]?.let {
                RowMovieItems(movie = it){ movieID ->
                    onMovieClick(movieID)
                }
            }
        }
    }


}