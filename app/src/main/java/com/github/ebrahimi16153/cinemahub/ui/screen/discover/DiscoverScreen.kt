package com.github.ebrahimi16153.cinemahub.ui.screen.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.github.ebrahimi16153.cinemahub.R
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.ui.componnet.GenreItem
import com.github.ebrahimi16153.cinemahub.ui.componnet.GridMovieItems
import com.github.ebrahimi16153.cinemahub.ui.componnet.RowMovieItems
import com.github.ebrahimi16153.cinemahub.utils.MyExtension.toGenre
import com.github.ebrahimi16153.cinemahub.utils.Route
import com.github.ebrahimi16153.cinemahub.viewmodel.DiscoverViewModel
import com.github.ebrahimi16153.cinemahub.viewmodel.SearchViewModel

@Composable
fun DiscoverScreen(
    navHostController: NavHostController,
    genreID: Int,
    genreName: String,
    isGrid: Boolean,
    movies: LazyPagingItems<Movie>,
    genres: List<Genre>,
    onGridClick: () -> Unit,
    onGenreClick: (Genre) -> Unit,
    discoverViewModel: DiscoverViewModel
) {


    var currentGenreID by remember { mutableIntStateOf(genreID) }
    var currentGenreName by remember { mutableStateOf(genreName) }

    //GET movies
    Column(modifier = Modifier.fillMaxWidth()) {
        //navigation and genres
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(56.dp), verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = stringResource(R.string.back_navigation)
                        )
                    }
                    Text(text = currentGenreName)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                IconButton(onClick = { onGridClick() }) {
                    Icon(imageVector = Icons.Rounded.Apps, contentDescription = "")
                }
            }

            // genreList


            if (genreID != -1) {
                GenresList(
                    currentGenre = currentGenreID,
                    genres = genres,
                    onGenreClick = { itGenre ->
                        onGenreClick(itGenre)
                        currentGenreID = itGenre.id
                        currentGenreName = itGenre.name.toString()
                    })
            }

            //MovieList
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                if (isGrid) {

                    DiscoverGridMovieList(movies = movies, onMovieClick = { itMovieId ->
                        navHostController.navigate(Route.Details.name + "/$itMovieId")
                    })
                } else {

                    DiscoverRowMovieList(movies = movies, onMovieClick = { itMovieId ->
                        navHostController.navigate(Route.Details.name + "/$itMovieId")

                    })

                }
                if (movies.loadState.append is LoadState.Loading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .background(color = MaterialTheme.colorScheme.primary),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            trackColor = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 1.dp,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
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
fun DiscoverGridMovieList(
    onMovieClick: (Int) -> Unit = {},
    movies: LazyPagingItems<Movie>
) {


    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(130.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalItemSpacing = 3.dp,
        horizontalArrangement = Arrangement.spacedBy(1.dp)

    ) {

        items(count = movies.itemCount) { itIndex ->

            key(movies){
                movies[itIndex]?.id
            }

            movies[itIndex]?.let {
                GridMovieItems(movie = it) { movieID ->
                    onMovieClick(movieID)
                }
            }
        }
    }
}


@Composable
fun DiscoverRowMovieList(movies: LazyPagingItems<Movie>, onMovieClick: (Int) -> Unit = {}) {


    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {


        items(count = movies.itemCount) { itIndex ->

            movies[itIndex]?.let {
                RowMovieItems(movie = it) { movieID ->
                    onMovieClick(movieID)
                }
            }
        }
    }


}