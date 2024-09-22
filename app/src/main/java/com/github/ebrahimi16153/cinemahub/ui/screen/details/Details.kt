package com.github.ebrahimi16153.cinemahub.ui.screen.details

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.MovieFilter
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.data.model.Credits
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.model.MovieImages
import com.github.ebrahimi16153.cinemahub.data.model.Trailers
import com.github.ebrahimi16153.cinemahub.data.wrapper.Wrapper
import com.github.ebrahimi16153.cinemahub.ui.componnet.CircleItems
import com.github.ebrahimi16153.cinemahub.ui.componnet.ErrorBox
import com.github.ebrahimi16153.cinemahub.ui.componnet.MyCircularProgress
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL
import com.github.ebrahimi16153.cinemahub.utils.findDirector
import com.github.ebrahimi16153.cinemahub.utils.findProducer
import com.github.ebrahimi16153.cinemahub.viewmodel.DetailsViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun Details(
    movieID: Int,
    navHostController: NavHostController,
    detailsViewModel: DetailsViewModel
) {


    /**
     * Detail screen must update eveyTime loaded for this we don't use from viewModel
     * viewModel cashed every thing until new data replaced whit old data
     * for this we use from repository directly
     *
     */


    val movieDetail = detailsViewModel.movieDetail.collectAsState()

    val movieImages = detailsViewModel.movieImages.collectAsState()

    val movieTrailers = detailsViewModel.trailers.collectAsState()

    val movieCredits = detailsViewModel.credits.collectAsState()

    val error = detailsViewModel.error.collectAsState()

    val isSaved = detailsViewModel.isMovieExist.collectAsState()


    LaunchedEffect(key1 = true) {

        detailsViewModel.getMovieDetail(movieID)

        detailsViewModel.getMovieImages(movieID)

        detailsViewModel.getMovieTrailers(movieID)

        detailsViewModel.getMovieCredits(movieID)

        detailsViewModel.getIsMovieExist(movieID)


    }

    when (error.value) {
        is Wrapper.Error -> {
            ErrorBox(message = (error.value as Wrapper.Error).message) {
                detailsViewModel.apply {
                    getMovieDetail(movieID)
                    getMovieImages(movieID)
                    getMovieTrailers(movieID)
                    getMovieCredits(movieID)
                }
            }
        }

        Wrapper.Idle -> {

            if (movieDetail.value is Wrapper.Loading || movieImages.value is Wrapper.Loading || movieTrailers.value is Wrapper.Loading || movieCredits.value is Wrapper.Loading) {
                MyCircularProgress()
            } else if (movieDetail.value is Wrapper.Success && movieImages.value is Wrapper.Success && movieTrailers.value is Wrapper.Success && movieCredits.value is Wrapper.Success) {
                DetailsOrientation(
                    movieDetail = (movieDetail.value as Wrapper.Success).data,
                    posters = (movieImages.value as Wrapper.Success).data,
                    trailers = (movieTrailers.value as Wrapper.Success).data,
                    navHostController = navHostController,
                    movieCredits = (movieCredits.value as Wrapper.Success).data,
                    isSave = isSaved.value,
                    onSaveClick = {
                        if (isSaved.value) {
                            detailsViewModel.deleteMovie((movieDetail.value as Wrapper.Success).data)
                        } else {
                            detailsViewModel.saveMovie((movieDetail.value as Wrapper.Success).data)
                        }

                    }
                )
            }


        }

        Wrapper.Loading -> {}
        is Wrapper.Success -> {}
    }


}

@Composable
fun DetailsOrientation(
    movieDetail: MovieDetail,
    posters: List<MovieImages.Poster?>,
    trailers: List<Trailers.Trailer>,
    navHostController: NavHostController,
    movieCredits: Credits,
    isSave: Boolean,
    onSaveClick: () -> Unit
) {


    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) {

        DetailsLandScape(
            movieDetail = movieDetail,
            posters = posters,
            navHostController = navHostController,
            trailers = trailers,
            movieCredits = movieCredits,
            isSave = isSave,
            onSaveClick = {
                onSaveClick()
            }

        )
    } else {
        DetailsPortrait(
            movieDetail = movieDetail,
            posters = posters,
            trailers = trailers,
            movieCredits = movieCredits,
            navHostController = navHostController,
            isSave = isSave,
            onSaveClick = {
                onSaveClick()
            }
        )

    }

}

@Composable
fun DetailsPortrait(
    movieDetail: MovieDetail,
    posters: List<MovieImages.Poster?>,
    trailers: List<Trailers.Trailer>,
    navHostController: NavHostController,
    movieCredits: Credits,
    isSave: Boolean,
    onSaveClick: () -> Unit
) {
    LazyColumn {
        item {
            MovieBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(650.dp),
                posters = posters,
                movieDetail = movieDetail,
                isSave = isSave,
                onNavClick = { navHostController.navigateUp() },
                onSaveClick = {
                    onSaveClick()
                }

            )
        }

        item {
            MovieTrailers(trailers = trailers)
            MovieIfo(
                movieDetail = movieDetail,
                movieCredits = movieCredits
            )


        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            MovieCast(movieCredits = movieCredits)
            Spacer(modifier = Modifier.height(10.dp))
            MovieCompany(movieDetail = movieDetail)
        }
    }


}

@Composable
fun DetailsLandScape(
    movieDetail: MovieDetail,
    posters: List<MovieImages.Poster?>,
    navHostController: NavHostController,
    trailers: List<Trailers.Trailer>,
    onSaveClick: () -> Unit,
    movieCredits: Credits,
    isSave: Boolean
) {

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            MovieBanner(
                modifier = Modifier.fillMaxSize(),
                posters = posters,
                movieDetail = movieDetail,
                isSave = isSave,
                onNavClick = { navHostController.navigateUp() },
                onSaveClick = { onSaveClick() })
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                MovieTrailers(trailers = trailers)
                MovieIfo(
                    movieDetail = movieDetail,
                    movieCredits = movieCredits,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Cast", modifier = Modifier.padding(horizontal = 16.dp))


            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
                MovieCast(movieCredits = movieCredits)
                Spacer(modifier = Modifier.height(10.dp))
                MovieCompany(movieDetail = movieDetail)
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieBanner(
    modifier: Modifier = Modifier,
    posters: List<MovieImages.Poster?>,
    movieDetail: MovieDetail,
    onSaveClick: () -> Unit,
    onNavClick: () -> Unit,
    isSave: Boolean
) {

    Box(modifier = modifier) {
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
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            //navigation & Save
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //navigation
                IconButton(onClick = { onNavClick() }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                //save
                IconButton(onClick = { onSaveClick() }) {
                    Icon(
                        imageVector = if (isSave) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = "",
                        tint = if (isSave) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                    )
                }
            }

            PosterInfo(movieDetail = movieDetail)
            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieTrailers(
    trailers: List<Trailers.Trailer>
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val officialTrailers: MutableList<Trailers.Trailer> = mutableListOf()
    trailers.forEach {
        if (it.official == true)
            officialTrailers.add(it)
    }

    if (officialTrailers.isNotEmpty()) {
        val state = rememberPagerState(pageCount = { officialTrailers.size })
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = " Official Trailers",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalPager(modifier = Modifier.fillMaxWidth(), state = state) { itIndex ->

                TrailerItems(trailer = officialTrailers[itIndex], lifecycleOwner = lifecycleOwner)

            }
        }
    }
}


@Composable
fun TrailerItems(
    trailer: Trailers.Trailer,
    lifecycleOwner: LifecycleOwner
) {

    AndroidView(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(RoundedCornerShape(15.dp)),
        factory = { itContext ->


            YouTubePlayerView(context = itContext).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(trailer.key!!, 0f)
                    }

                    override fun onApiChange(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.pause()
                    }
                })
            }
        })


}


@Composable
fun PosterItems(poster: MovieImages.Poster) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth(),
        model = IMAGE_URL + poster.filePath,
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun PosterInfo(movieDetail: MovieDetail) {

    val brush = Brush.verticalGradient(
        listOf(
            Color.Transparent,
            Color.Transparent,
            MaterialTheme.colorScheme.background
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush), contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //title
            movieDetail.title?.let {
                Text(text = it, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(10.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //rate
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = movieDetail.voteAverage.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )

                }
                //duration
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.AccessTime,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = movieDetail.runtime.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )

                }

                //status
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.MovieFilter,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = movieDetail.status.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )

                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Rounded.Language,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = movieDetail.productionCountries.toString().removePrefix("[")
                            .removeSuffix("]"),
                        style = MaterialTheme.typography.bodySmall
                    )

                }

            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun MovieIfo(movieDetail: MovieDetail, movieCredits: Credits) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Overview",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier.padding(16.dp),
            text = movieDetail.overview.toString()
        )
        Spacer(modifier = Modifier.height(5.dp))




        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "Director: ${movieCredits.crew.findDirector()}"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "Producer: ${movieCredits.crew.findProducer()}"
        )

    }
}

@Composable
fun MovieCast(movieCredits: Credits) {

    LazyRow(
        contentPadding = PaddingValues(16.dp),

        ) {
        items(items = movieCredits.cast) { itCast ->

            itCast.name?.let { CircleItems(name = it, imagePath = IMAGE_URL + itCast.profilePath) }

        }


    }


}

@Composable
fun MovieCompany(movieDetail: MovieDetail) {
    LazyRow(
        horizontalArrangement = Arrangement.Center
    ) {
        items(items = movieDetail.productionCompanies!!) {
            CircleItems(name = it.name.toString(), imagePath = IMAGE_URL + it.logoPath)
        }
    }
}



