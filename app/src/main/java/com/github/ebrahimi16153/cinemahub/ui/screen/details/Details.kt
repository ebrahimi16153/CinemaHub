package com.github.ebrahimi16153.cinemahub.ui.screen.details

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.data.model.ImageCollection
import com.github.ebrahimi16153.cinemahub.data.wrapper.Wrapper
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL
import com.github.ebrahimi16153.cinemahub.viewmodel.DetailsViewModel

@Composable
fun Details(
    movieID: Int,
    detailsViewModel: DetailsViewModel,
    navHostController: NavHostController
) {

    if (movieID != -1) {
        detailsViewModel.getMovieDetails(movieID)
    }

    val movieDetail = detailsViewModel.movie.collectAsState()
    val collectionID = movieDetail.value?.belongsToCollection?.id

        detailsViewModel.getCollectionImages(collectionID ?: -1)

    val posters by detailsViewModel.posters.collectAsState()


//    if (!navHostController.currentDestination?.route.equals("details/$movieID") && posters.isEmpty()){
//          detailsViewModel.setPoster()
//    }

    LazyColumn(verticalArrangement = Arrangement.Top) {
        //Banner
        item {
            when (posters) {
                is Wrapper.Error -> {
                    Text(text = (posters as Wrapper.Error).message)
                }

                Wrapper.Idle -> {}
                Wrapper.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )

                    }
                }

                is Wrapper.Success -> {
                    MovieBanner(posters = (posters as Wrapper.Success).data)
                }
            }
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun MovieBanner(
    posters: List<ImageCollection.Poster> = emptyList(),
    onPosterClick: (String) -> Unit = {}
) {
    val state = rememberPagerState(pageCount = { posters.size })
    HorizontalPager(modifier = Modifier.fillMaxWidth(), state = state) { itIndex ->
        PosterItems(posters[itIndex])
    }


}

@Composable
fun PosterItems(poster: ImageCollection.Poster) {
    AsyncImage(
        modifier = Modifier.fillMaxWidth(),
        model = IMAGE_URL + poster.filePath,
        contentDescription = ""
    )
}
