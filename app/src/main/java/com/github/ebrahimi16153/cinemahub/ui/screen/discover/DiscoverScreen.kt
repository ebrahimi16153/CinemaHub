package com.github.ebrahimi16153.cinemahub.ui.screen.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.ui.componnet.GenreItem
import com.github.ebrahimi16153.cinemahub.ui.screen.home.HorizontalGenresList
import com.github.ebrahimi16153.cinemahub.viewmodel.DiscoverViewModel

@Composable
fun DiscoverScreen(
    navHostController: NavHostController,
    genreID: Int,
    discoverViewModel: DiscoverViewModel
){
Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

    if (genreID != -1) {

        val genres by discoverViewModel.genres.collectAsState()

        Column(modifier = Modifier.fillMaxSize()) {

            GenresList(currentGenre = genreID, genres = genres , onGenreClick = {itGenreID -> })

        }

    }


}
}


@Composable
fun GenresList(currentGenre:Int,genres: List<Genre>, onGenreClick: (Int) -> Unit) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between items
    ) {
        itemsIndexed(genres, key = { _, movie -> movie.id }) { _, genre ->
            GenreItem(isSelected = currentGenre == genre.id, genre = genre, onGenreClick = { onGenreClick(it) })
        }
    }

}