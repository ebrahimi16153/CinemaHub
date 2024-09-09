package com.github.ebrahimi16153.cinemahub.ui.screen.details

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.MovieFilter
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.data.model.Credits
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.model.MovieImages
import com.github.ebrahimi16153.cinemahub.data.model.Trailers
import com.github.ebrahimi16153.cinemahub.data.repository.DetailsRepository
import com.github.ebrahimi16153.cinemahub.data.wrapper.Wrapper
import com.github.ebrahimi16153.cinemahub.ui.componnet.MyCircularProgress
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

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

    val movieImages: MutableState<Wrapper<List<MovieImages.Poster>>> = remember {
        mutableStateOf(Wrapper.Loading)
    }

    val movieTrailers: MutableState<Wrapper<List<Trailers.Trailer>>> = remember {
        mutableStateOf(Wrapper.Loading)
    }
//    val movieCredits: MutableState<Wrapper<Credits>> = remember {
//        mutableStateOf(Wrapper.Loading)
//    }


    LaunchedEffect(key1 = true) {

        detailsRepository.getMovieDetailsByMovieID(movieID).collect { itMovieDetail ->
            movieDetail.value = itMovieDetail
        }

        detailsRepository.getMovieImages(movieID = movieID).collect { itImages ->
            if (itImages != null) {
                movieImages.value = Wrapper.Success(data = itImages.posters)
            }
        }

        detailsRepository.getMovieTrailers(movieID).collect {
            if (it != null) {
                movieTrailers.value = Wrapper.Success(data = it.results)

            }
        }

//        detailsRepository.getCredits(movieID).collect { itCredits ->
//            if (itCredits != null) {
//                movieCredits.value = Wrapper.Success(data = itCredits)
//            }
//        }
    }


    when (movieImages.value) {
        is Wrapper.Error -> {
            Text(text = (movieImages.value as Wrapper.Error).message)
        }

        Wrapper.Idle -> {}
        Wrapper.Loading -> {
            MyCircularProgress()
        }

        is Wrapper.Success -> {


            movieDetail.value?.let {

                if (movieTrailers.value is Wrapper.Success) {

                    DetailsOrientation(
                        movieDetail = it,
                        posters = (movieImages.value as Wrapper.Success).data,
                        trailers = (movieTrailers.value as Wrapper.Success).data,
//                        movieCredits = (movieCredits.value as Wrapper.Success).data,
                        navHostController = navHostController
                    )
                }


            }

        }
    }


}

@Composable
fun DetailsOrientation(
    movieDetail: MovieDetail,
    posters: List<MovieImages.Poster?>,
    trailers: List<Trailers.Trailer>,
//    movieCredits: Credits,
    navHostController: NavHostController
) {


    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) {

        DetailsLandScape(
            movieDetail = movieDetail,
            posters = posters,
            navHostController = navHostController,
            trailers = trailers,
//            movieCredits = movieCredits,
            onSaveClick = { itPosters, itMovieDetail ->

            }

        )
    } else {
        DetailsPortrait(
            movieDetail = movieDetail,
            posters = posters,
            trailers = trailers,
//            movieCredits = movieCredits,
            navHostController = navHostController
        )

    }

}

@Composable
fun DetailsPortrait(
    movieDetail: MovieDetail,
    posters: List<MovieImages.Poster?>,
    trailers: List<Trailers.Trailer>,
    navHostController: NavHostController,
//    movieCredits: Credits
) {
    LazyColumn {
        item {
            MovieBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(650.dp),
                posters = posters,
                movieDetail = movieDetail,
                onNavClick = { navHostController.navigateUp() },
                onSaveClick = { _, _ -> }

            )
        }

        item {
            MovieTrailers(trailers = trailers)
        }
        item {
            MovieIfo(movieDetail = movieDetail,
//                movieCredits = movieCredits
            )
        }

    }


}

@Composable
fun DetailsLandScape(
    movieDetail: MovieDetail,
    posters: List<MovieImages.Poster?>,
    navHostController: NavHostController,
    trailers: List<Trailers.Trailer>,
    onSaveClick: (List<MovieImages.Poster?>, MovieDetail) -> Unit,
//    movieCredits: Credits
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
                onNavClick = { navHostController.navigateUp() },
                onSaveClick = { itPosters, itMovieDetail -> onSaveClick(itPosters, itMovieDetail) })
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                MovieTrailers(trailers = trailers)
            }
            item {
                MovieIfo(movieDetail = movieDetail,
//                    movieCredits = movieCredits
                )
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
    onSaveClick: (List<MovieImages.Poster?>, MovieDetail) -> Unit = { _, _ -> },
    onNavClick: () -> Unit
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
                FilledIconButton(onClick = { onNavClick() }) {
                    Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = "")
                }
                //save
                FilledIconButton(onClick = { onSaveClick(posters, movieDetail) }) {
                    Icon(imageVector = Icons.Rounded.Bookmark, contentDescription = "")
                }
            }

            PosterInfo(movieDetail = movieDetail)
        }


    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieTrailers(
    trailers: List<Trailers.Trailer>
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val officialTrailers: MutableList<Trailers.Trailer>? = mutableListOf()
    trailers.forEach {
        if (it.official == true)
            officialTrailers?.add(it)
    }

    if (!officialTrailers.isNullOrEmpty()) {
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
        .padding(8.dp)
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

//            binding.webView.loadData(encodedHtml, "text/html", "base64")
//            binding.webView.settings.javaScriptEnabled = true

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
                //country
                var country by remember {
                    mutableStateOf("")
                }

                movieDetail.productionCountries.forEach { itCountry ->
                    country += itCountry.name + ","
                }

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
                        text = country,
                        style = MaterialTheme.typography.bodySmall
                    )

                }

            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun MovieIfo(movieDetail: MovieDetail) {
    Column(modifier = Modifier.fillMaxWidth()) {

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

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Director & Producer",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var director by remember {
                mutableStateOf("")
            }

            var producer by remember {
                mutableStateOf("")
            }

//            if(movieCredits.crew.isNotEmpty()){
//                movieCredits.crew.forEach { itCrew ->
//                    if (itCrew.job == "Producer"){
//                        producer += itCrew.name+" "
//                    }else if ( itCrew.job == "Director"){
//                        director += itCrew.name+" "
//                    }
//                }
//            }
//
//
//            Text(text = "Directors: $director")
//            Text(text = "Directors: $producer")


        }


    }


}



