package com.github.ebrahimi16153.cinemahub.ui.componnet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL
import java.util.Locale

@Composable
fun GridMovieItems(movie: Movie, onMovieClick: (Int) -> Unit = {}) {

    val brush = Brush.verticalGradient(
        listOf(
            Color.Transparent,
            Color.Transparent,
            MaterialTheme.colorScheme.background
        )
    )
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(200.dp)
            .padding(horizontal = 2.dp)
            .clickable { onMovieClick(movie.id) }
            .clip(RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = IMAGE_URL+movie.posterPath,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val averageRate = String.format(Locale.US, "%.1f", movie.voteAverage).toDoubleOrNull()?:0.0
                val movieDetail = "$averageRate from ${movie.voteCount?:0} | ${movie.originalLanguage?.uppercase()?:""} "
                Text(
                    text = movieDetail,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelMedium
                )
                movie.title?.let { Text(text = it, maxLines = 1, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center) }

            }
        }
    }
}


@Composable
fun RowMovieItems(
    movie: Movie,
    onMovieClick: (Int) -> Unit
){

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp).clickable { onMovieClick(movie.id) }, shape = RoundedCornerShape(10.dp)) {
        Row {
            AsyncImage(modifier = Modifier.width(130.dp), model = IMAGE_URL+movie.posterPath, contentDescription ="" )
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                val averageRate = String.format(Locale.US, "%.1f", movie.voteAverage).toDoubleOrNull()?:0.0
                val movieDetail = "$averageRate from ${movie.voteCount?:0} | ${movie.originalLanguage?.uppercase()?:""} "
                movie.title?.let { Text(text = it) }
                 Text(text = movieDetail)
                movie.overview?.let { Text(text = it, style = MaterialTheme.typography.bodySmall, maxLines = 5, overflow = TextOverflow.Ellipsis) }
            }
        }


    }



}