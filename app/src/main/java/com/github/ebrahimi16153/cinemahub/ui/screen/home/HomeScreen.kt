package com.github.ebrahimi16153.cinemahub.ui.screen.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.github.ebrahimi16153.cinemahub.R
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.ui.componnet.BannerItems
import com.github.ebrahimi16153.cinemahub.ui.componnet.GridMovieItems
import com.github.ebrahimi16153.cinemahub.utils.IMAGE_URL
import com.github.ebrahimi16153.cinemahub.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navHostController: NavHostController, homeViewModel: HomeViewModel) {

    ////////////////////handel orientation of screen /////////////////////////////////

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) {

        //landscape design
        LandscapeHome(navHostController = navHostController, homeViewModel)


    } else {

        // portrait design
        PortraitHome(navHostController = navHostController,homeViewModel= homeViewModel)


    }

}


@Composable
fun PortraitHome(navHostController: NavHostController,homeViewModel: HomeViewModel) {

    homeViewModel.getMovieOFBanner()
    val movies by homeViewModel.movieOfBanner.collectAsState()

    val connection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                return super.onPreScroll(available, source)
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(connection)) {
        //bannerSection
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            MyBanner(isLandscape = false, movies = movies) {
                //todo onClick
            }
            HomeTopBar()
        }

        MovieSection(homeViewModel = homeViewModel)


    }
}


@Composable
fun LandscapeHome(navHostController: NavHostController, homeViewModel: HomeViewModel) {

    homeViewModel.getMovieOFBanner()
    val movies by homeViewModel.movieOfBanner.collectAsState()


    Row(modifier = Modifier.fillMaxSize()) {
        //banner section
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f), contentAlignment = Alignment.TopCenter
        ) {

            MyBanner(isLandscape = true, movies = movies) {
                //todo onClick
            }
            HomeTopBar()


        }
        // movie section
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.6f), contentAlignment = Alignment.Center
        ) {
           MovieSection(homeViewModel = homeViewModel)

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
fun MovieSection(homeViewModel: HomeViewModel){

    //get movies
    homeViewModel.getNowPlayingMovies()
    val nowPlayingMovieList by homeViewModel.nowPlayingMovie.collectAsState()

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start){
        //nowPlaying
        Text(text = stringResource(id = R.string.playing_now), style = MaterialTheme.typography.bodyMedium)
        VerticalMovieList(movies = nowPlayingMovieList)

    }


}

@Composable
fun VerticalMovieList(movies: List<Movie>){
    LazyRow {

        items(items = movies){ itMovie ->
            GridMovieItems(movie = itMovie)
        }

    }
}

