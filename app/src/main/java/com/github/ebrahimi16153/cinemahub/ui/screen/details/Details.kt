package com.github.ebrahimi16153.cinemahub.ui.screen.details

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.data.model.ImageCollection
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.repository.DetailsRepository
import com.github.ebrahimi16153.cinemahub.data.wrapper.Wrapper
import com.github.ebrahimi16153.cinemahub.ui.componnet.MyCircularProgress
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL
import kotlinx.coroutines.flow.catch

@Composable
fun Details(
    movieID: Int,
    navHostController: NavHostController,
    detailsRepository: DetailsRepository
) {


    /**
     *
     * Detail screen must update eveyTime loaded for this we don't use from viewModel
     * viewModel cashed every thing until new data replaced whit old data
     *
     * for this we use from repository directly
     *
     */


    val movieDetail: MutableState<MovieDetail?> = remember {
        mutableStateOf(null)
    }
    val moviePoster: MutableState<Wrapper<List<ImageCollection.Poster>>> = remember {
        mutableStateOf(Wrapper.Loading)
    }

    var isPosterAvailable by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = true) {

        detailsRepository.getMovieDetailsByMovieID(movieID).collect { itMovieDetail ->
            movieDetail.value = itMovieDetail
        }
        val posterID = movieDetail.value?.belongsToCollection?.id ?: -1

            detailsRepository.getCollectionImages(posterID).catch {
                moviePoster.value = Wrapper.Error(message = it.message.toString())
            }.collect { itCollection ->

                if (itCollection != null) {
                    moviePoster.value = Wrapper.Success(data = itCollection.posters?: emptyList())
                }else{
                    moviePoster.value = Wrapper.Success(data = emptyList())
                }


            }

    }


    when (moviePoster.value) {
        is Wrapper.Error -> {
            Text(text = (moviePoster.value as Wrapper.Error).message)
        }

        Wrapper.Idle -> {}
        Wrapper.Loading -> {
            MyCircularProgress()
        }

        is Wrapper.Success -> {
            LazyColumn {
                item {
                    MovieBanner(
                        posters = (moviePoster.value as Wrapper.Success<List<ImageCollection.Poster>>).data,
                        movieDetail = movieDetail.value!!
                    )
                }
            }
        }
    }


}

@Composable
fun DetailsOrientation(
    isPoserAvailable: Boolean,
    movieDetail: MovieDetail,
    posters: List<ImageCollection.Poster?>,
    navHostController: NavHostController
) {


    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) {
        DetailsPortrait(
            isPoserAvailable = isPoserAvailable,
            movieDetail = movieDetail,
            posters = posters,
            navHostController = navHostController
        )
    } else {
        DetailsLandScape(
            isPoserAvailable = isPoserAvailable,
            movieDetail = movieDetail,
            posters = posters,
            navHostController = navHostController
        )
    }

}

@Composable
fun DetailsPortrait(
    isPoserAvailable: Boolean,
    movieDetail: MovieDetail,
    posters: List<ImageCollection.Poster?>,
    navHostController: NavHostController
) {
}

@Composable
fun DetailsLandScape(
    isPoserAvailable: Boolean,
    movieDetail: MovieDetail,
    posters: List<ImageCollection.Poster?>,
    navHostController: NavHostController
) {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieBanner(
    posters: List<ImageCollection.Poster>,
    movieDetail: MovieDetail,
    onPosterClick: (String) -> Unit = {}
) {

    if (posters.isNotEmpty()) {
        val state = rememberPagerState(pageCount = { posters.size })
        HorizontalPager(modifier = Modifier.fillMaxWidth(), state = state) { itIndex ->
            PosterItems(posters[itIndex])
        }
    } else {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = IMAGE_URL + movieDetail.posterPath,
            contentDescription = ""
        )
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


