package com.github.ebrahimi16153.cinemahub.ui.screen.discover

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
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
import com.github.ebrahimi16153.cinemahub.R
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.ui.componnet.GenreItem
import com.github.ebrahimi16153.cinemahub.ui.screen.search.GridMovieList
import com.github.ebrahimi16153.cinemahub.utils.MyExtension.toGenre
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

    //GET movies
    discoverViewModel.getMoviesByGenre(currentGenreID.toString())
    Log.e("TAG3", "DiscoverScreen: $currentGenreName", )
    val movies by discoverViewModel.moviesByGenre.collectAsState()
    Log.e("TAG4", "DiscoverScreen: ${movies.toString()}", )


    Column(modifier = Modifier.fillMaxWidth()) {
        //navigation and genres
        Column(modifier = Modifier
            .fillMaxSize()
            .height(56.dp), verticalArrangement = Arrangement.Top) {
             Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                 IconButton(onClick = { navHostController.popBackStack()}) {
                     Icon(
                         imageVector = Icons.Rounded.ArrowBackIosNew,
                         contentDescription = stringResource(R.string.back_navigation))
                 }
                 Text(text = currentGenreName)
                 Spacer(modifier = Modifier.height(10.dp))
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
            GridMovieList(movies = movies,onMovieClick = {})




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