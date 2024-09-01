package com.github.ebrahimi16153.cinemahub.ui.screen.details

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
     * Detail screen must update eveyTime loaded for this we don't use from viewModel
     * viewModel cashed every thing until new data replaced whit old data
     * for this we use from repository directly
     *
     */


    val movieDetail: MutableState<MovieDetail?> = remember {
        mutableStateOf(null)
    }
    val moviePoster: MutableState<Wrapper<List<ImageCollection.Poster>>> = remember {
        mutableStateOf(Wrapper.Loading)
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
                    moviePoster.value = Wrapper.Success(data = itCollection.posters)
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


            movieDetail.value?.let {
                DetailsOrientation(
                    movieDetail = it,
                    posters = (moviePoster.value as Wrapper.Success).data,
                    navHostController = navHostController
                )
            }

        }
    }


}

@Composable
fun DetailsOrientation(
    movieDetail: MovieDetail,
    posters: List<ImageCollection.Poster?>,
    navHostController: NavHostController
) {


    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) {

        DetailsLandScape(
            movieDetail = movieDetail,
            posters = posters,
            navHostController = navHostController,
            onSaveClick = { itPosters, itMovieDetail ->

            }

        )
    } else {
        DetailsPortrait(
            movieDetail = movieDetail,
            posters = posters,
            navHostController = navHostController
        )

    }

}

@Composable
fun DetailsPortrait(
    movieDetail: MovieDetail,
    posters: List<ImageCollection.Poster?>,
    navHostController: NavHostController
) {
    LazyColumn {
        item {
            MovieBanner(
                modifier = Modifier.fillMaxWidth(),
                posters = posters,
                movieDetail = movieDetail,
                onNavClick = { navHostController.navigateUp() },
                onSaveClick = { _, _ -> }

            )
        }
    }


}

@Composable
fun DetailsLandScape(
    movieDetail: MovieDetail,
    posters: List<ImageCollection.Poster?>,
    navHostController: NavHostController,
    onSaveClick: (List<ImageCollection.Poster?>, MovieDetail) -> Unit
) {

    Row(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight(), contentAlignment = Alignment.Center) {
            MovieBanner(
                modifier = Modifier.fillMaxSize(),
                posters = posters,
                movieDetail = movieDetail,
                onNavClick = { navHostController.navigateUp() },
                onSaveClick = {itPosters,itMovieDetail -> onSaveClick(itPosters,itMovieDetail) })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieBanner(
    modifier: Modifier = Modifier,
    posters: List<ImageCollection.Poster?>,
    movieDetail: MovieDetail,
    onSaveClick: (List<ImageCollection.Poster?>,MovieDetail) -> Unit = { _,_ ->},
    onNavClick:() -> Unit
) {

    Box(modifier = modifier, Alignment.TopCenter) {
        // poster
        if (posters.isNotEmpty()) {
            val state = rememberPagerState(pageCount = { posters.size })
            HorizontalPager(modifier = Modifier.fillMaxWidth(), state = state) { itIndex ->
                posters[itIndex]?.let { PosterItems(it) }
            }
        } else {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = IMAGE_URL + movieDetail.posterPath,
                contentDescription = ""
            )
        }
        //navigation & Save
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            //navigation
            FilledIconButton(onClick = { onNavClick() }) {
                Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = "" ) }
            //save
            FilledIconButton(onClick = { onSaveClick(posters,movieDetail) }) {
                Icon(imageVector = Icons.Rounded.Bookmark, contentDescription = "" )
            }
        }
    }
}

@Composable
fun PosterItems(poster: ImageCollection.Poster) {
    AsyncImage(
        modifier = Modifier.fillMaxWidth(),
        model = IMAGE_URL + poster.filePath,
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
}


