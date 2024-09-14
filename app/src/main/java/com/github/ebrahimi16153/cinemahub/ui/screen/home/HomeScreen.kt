package com.github.ebrahimi16153.cinemahub.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Query
import com.github.ebrahimi16153.cinemahub.R
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.wrapper.Wrapper
import com.github.ebrahimi16153.cinemahub.ui.componnet.BannerItems
import com.github.ebrahimi16153.cinemahub.ui.componnet.GenreItem
import com.github.ebrahimi16153.cinemahub.ui.componnet.GridMovieItems
import com.github.ebrahimi16153.cinemahub.ui.componnet.MyCircularProgress
import com.github.ebrahimi16153.cinemahub.utils.Route
import com.github.ebrahimi16153.cinemahub.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navHostController: NavHostController, homeViewModel: HomeViewModel) {


    ////////////////////getData////////////////////////////////////////////////////
    val movies by homeViewModel.movieOfBanner.collectAsState()
    val genres by homeViewModel.genres.collectAsState()
    val nowPlayingMovieList by homeViewModel.nowPlayingMovie.collectAsState()
    val topRateMovie by homeViewModel.topRateMovie.collectAsState()
    val popularMovie by homeViewModel.popularMovie.collectAsState()
    val upcomingMovie by homeViewModel.upcomingMovie.collectAsState()
    val error by homeViewModel.error.collectAsState()

    ////////////////////handel orientation of screen /////////////////////////////////

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


    if (movies is Wrapper.Loading || genres is Wrapper.Loading ||
        nowPlayingMovieList is Wrapper.Loading || topRateMovie is Wrapper.Loading ||
        popularMovie is Wrapper.Loading || upcomingMovie is Wrapper.Loading) {

        MyCircularProgress()

    } else {
        if (error is Wrapper.Error){
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Text(text = (error as Wrapper.Error).message.toString())
            }
        }else{

            if (isLandscape) {

                //landscape design
                LandscapeHome(
                    navHostController = navHostController,
                    mainBannerMovies = (movies as Wrapper.Success).data,
                    nowPlayingMovieList = (nowPlayingMovieList as Wrapper.Success).data,
                    topRateMovie = (topRateMovie as Wrapper.Success).data,
                    popularMovie = (popularMovie as Wrapper.Success).data,
                    upcomingMovie = (upcomingMovie as Wrapper.Success).data,
                    genres = (genres as Wrapper.Success).data
                )

            } else {

                // portrait design
                PortraitHome(
                    navHostController = navHostController,
                    mainBannerMovies = (movies as Wrapper.Success).data,
                    nowPlayingMovieList = (nowPlayingMovieList as Wrapper.Success).data,
                    topRateMovie = (topRateMovie as Wrapper.Success).data,
                    popularMovie = (popularMovie as Wrapper.Success).data,
                    upcomingMovie = (upcomingMovie as Wrapper.Success).data,
                    genres = (genres as Wrapper.Success).data
                )
            }
        }


    }
}


@Composable
fun PortraitHome(
    navHostController: NavHostController,
    mainBannerMovies: List<Movie>,
    nowPlayingMovieList: List<Movie>,
    topRateMovie: List<Movie>,
    popularMovie: List<Movie>,
    upcomingMovie: List<Movie>,
    genres: List<Genre>
) {

    LazyColumn {
        item {
            //bannerSection
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                MyBanner(isLandscape = false, movies = mainBannerMovies) {
                    //todo onClick
                }
                Column {
                    HomeTopBar()
                    HorizontalGenresList(
                        genres = genres,
                        onGenreClick = { itGenre ->
                            navHostController.navigate(Route.Discover.name + "/${itGenre.id}/${itGenre.name}")
                        })
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            MovieSection(
                nowPlayingMovieList = nowPlayingMovieList,
                topRateMovie = topRateMovie,
                popularMovie = popularMovie,
                upcomingMovie = upcomingMovie,
                navHostController = navHostController
            )
        }
    }
}


@Composable
fun LandscapeHome(
    navHostController: NavHostController,
    topRateMovie: List<Movie>,
    nowPlayingMovieList: List<Movie>,
    popularMovie: List<Movie>,
    upcomingMovie: List<Movie>,
    mainBannerMovies: List<Movie>,
    genres: List<Genre>
) {


    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //banner section
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f), contentAlignment = Alignment.TopCenter
        ) {

            MyBanner(isLandscape = true, movies = mainBannerMovies) { itMovieId ->
                navHostController.navigate(Route.Details.name + "/$itMovieId")
            }
            Column {
                HomeTopBar()
                HorizontalGenresList(
                    genres = genres,
                    onGenreClick = { itGenre ->
                        navHostController.navigate(Route.Discover.name + "/${itGenre.id}/${itGenre.name}")
                    })
            }
        }
        // movie section
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            item {
                MovieSection(
                    nowPlayingMovieList = nowPlayingMovieList,
                    topRateMovie = topRateMovie,
                    popularMovie = popularMovie,
                    upcomingMovie = upcomingMovie,
                    navHostController = navHostController
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HomeTopBar() {

    TopAppBar(
        colors = TopAppBarDefaults
            .topAppBarColors(containerColor = Color.Transparent),
        title = {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Image(

                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(50.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ebrahimi16153),
                    contentDescription = stringResource(id = R.string.home_top_profile)
                )
            }

        })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyBanner(isLandscape: Boolean, movies: List<Movie>, onBannerClick: (Int) -> Unit) {

    val state = rememberPagerState(pageCount = { movies.size })

    HorizontalPager(state = state) { index ->

        BannerItems(isLandscape, movies[index], onBannerClick = onBannerClick)

    }
}

@Composable
fun MovieSection(
    navHostController: NavHostController,
    nowPlayingMovieList: List<Movie>,
    topRateMovie: List<Movie>,
    popularMovie: List<Movie>,
    upcomingMovie: List<Movie>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        //nowPlaying
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.playing_now)
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalMovieList(
            movies = nowPlayingMovieList,
            onMovieClick = { navHostController.navigate(Route.Details.name + "/$it") })
        // topRate
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.top_movies)
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalMovieList(
            movies = topRateMovie,
            onMovieClick = { navHostController.navigate(Route.Details.name + "/$it") })
        // popular
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.popular)
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalMovieList(
            movies = popularMovie,
            onMovieClick = { navHostController.navigate(Route.Details.name + "/$it") })

        // upComing
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.coming_soon)
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalMovieList(
            movies = upcomingMovie,
            onMovieClick = { navHostController.navigate(Route.Details.name + "/$it") })

    }
}

@Composable
fun HorizontalMovieList(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp), // Consistent padding
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between items
    ) {

        itemsIndexed(items = movies, key = { _, movie -> movie.id!! }) { _, itMovie ->

            GridMovieItems(movie = itMovie, onMovieClick = { onMovieClick(itMovie.id!!) })

        }
    }
}


@Composable
fun HorizontalGenresList(genres: List<Genre>, onGenreClick: (Genre) -> Unit) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between items
    ) {
        itemsIndexed(genres, key = { _, movie -> movie.id }) { _, genre ->
            GenreItem(isSelected = false, genre = genre, onGenreClick = { onGenreClick(it) })
        }
    }

}

