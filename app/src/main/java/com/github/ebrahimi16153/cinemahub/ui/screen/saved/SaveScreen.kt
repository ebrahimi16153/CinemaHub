package com.github.ebrahimi16153.cinemahub.ui.screen.saved

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL
import com.github.ebrahimi16153.cinemahub.utils.Route
import com.github.ebrahimi16153.cinemahub.viewmodel.SavedViewModel
import java.util.Locale

@Composable
fun SaveScreen(savedViewModel: SavedViewModel,navHostController: NavHostController){
    LaunchedEffect(key1 = true) {
        savedViewModel.getAllMovies()
    }
   val movies = savedViewModel.movies.collectAsState()
    SavedMovieList(navHostController = navHostController, movies = movies.value)
}



@Composable
fun SavedMovieList(navHostController: NavHostController,movies:List<MovieDetail>){

        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)){

            items(items = movies){ itMovie ->
                SavedMovieItems(movieDetail = itMovie, onMovieClick = {
                    navHostController.navigate(Route.Details.name+"/${itMovie.id}")
                })
            }
        }




}

@Composable
fun SavedMovieItems(movieDetail: MovieDetail,onMovieClick: (Int) -> Unit){


    Surface(modifier = Modifier
        .fillMaxWidth()
        .clickable { onMovieClick(movieDetail.id) },
    ) {
        Row {
            AsyncImage(modifier = Modifier.width(130.dp).padding(vertical = 5.dp).clip(
                RoundedCornerShape(8.dp)
            ), model = IMAGE_URL +movieDetail.posterPath, contentDescription ="" )
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                val averageRate = String.format(Locale.US, "%.1f", movieDetail.voteAverage).toDoubleOrNull()?:0.0
                val movieInfo = "$averageRate from ${movieDetail.voteCount?:0} | ${movieDetail.originalLanguage?.uppercase()?:""} "
                movieDetail.title?.let { Text(text = it, textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Clip) }
                Text(text = movieInfo)
                movieDetail.overview?.let { Text(text = it, style = MaterialTheme.typography.bodySmall, maxLines = 5, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center) }
            }
        }


    }


}