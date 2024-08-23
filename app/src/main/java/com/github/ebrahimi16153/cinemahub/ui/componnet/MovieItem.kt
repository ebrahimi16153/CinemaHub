package com.github.ebrahimi16153.cinemahub.ui.componnet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL

@Composable
fun GridMovieItems(movie: Movie, onMovieClick: (Int) -> Unit = {}) {

    val brush = Brush.verticalGradient(
        listOf(
            MaterialTheme.colorScheme.background,
            Color.Transparent,
            MaterialTheme.colorScheme.background
        )
    )
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(200.dp)
            .clickable { movie.id?.let { onMovieClick(it) } }
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
                Text(
                    text = "9.3  2:57 US|EN",
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelMedium
                )
                movie.title?.let { Text(text = it, maxLines = 1, overflow = TextOverflow.Clip) }

            }
        }
    }
}