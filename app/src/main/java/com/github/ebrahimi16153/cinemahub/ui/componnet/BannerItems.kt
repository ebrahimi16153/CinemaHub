package com.github.ebrahimi16153.cinemahub.ui.componnet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.R
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL

@Composable
fun BannerItems(isLandscape: Boolean, movie: Movie, onBannerClick: (Int) -> Unit) {
    val landscapeModifier = Modifier.fillMaxSize()
    Box{
        AsyncImage(
            modifier = if (isLandscape) {
                landscapeModifier

            } else {
                Modifier
                    .fillMaxWidth()
                    .height(550.dp)
            },
            model = IMAGE_URL + movie.posterPath,
            contentDescription = stringResource(
                id = R.string.poster_of_main_banner
            ),
            contentScale = ContentScale.Crop

        )

        //shadow & content
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
            val brush = Brush.verticalGradient(
                listOf(
                    MaterialTheme.colorScheme.background,
                    Color.Transparent,
                    MaterialTheme.colorScheme.background
                )
            )
//            topShadow
            Box(
                modifier = if (isLandscape) {
                    landscapeModifier
                } else {

                    Modifier
                        .fillMaxWidth()
                        .height(550.dp)
                }.background(brush = brush),
                contentAlignment = Alignment.BottomCenter
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    movie.title?.let { Text(text = it) }
                    Button(onClick = { movie.id?.let { onBannerClick(it) } }) {
                        Text(text = stringResource(id = R.string.watch_now))
                    }
                    Text(text = stringResource(id = R.string.exclusive))
                    Spacer(modifier = Modifier.height(20.dp))
                }

            }


        }


    }


}