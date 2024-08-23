package com.github.ebrahimi16153.cinemahub.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {


    init {
        //get movies
        getNowPlayingMovies()
        getTopRateMovie()
        getPopularMovie()
        getUpcomingMovie()
        getMovieOFBanner()

    }

    ///////////////////////////////Movies OF BANNER////////////////////////////
    private val _movieOfBanner = MutableStateFlow<List<Movie>>(emptyList())
    val movieOfBanner: StateFlow<List<Movie>> = _movieOfBanner


    fun getMovieOFBanner() = viewModelScope.launch {
        homeRepository.trendingMovie().collectLatest { itMoves ->
            _movieOfBanner.value = itMoves
        }
    }

    ///////////////////////////////NOW PLAYING////////////////////////////////
    private val _nowPlayingMovie = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlayingMovie: StateFlow<List<Movie>> = _nowPlayingMovie

    fun getNowPlayingMovies() = viewModelScope.launch {
        homeRepository.nowPlayingMovie().collectLatest { itMovies ->
            _nowPlayingMovie.value = itMovies
        }
    }

    ////////////////////////////////////TOP RATE MOVIE/////////////////////////////////

    private val _topRateMovie = MutableStateFlow<List<Movie>>(emptyList())
    val topRateMovie : StateFlow<List<Movie>> = _topRateMovie

    fun getTopRateMovie() = viewModelScope.launch {

        homeRepository.getTopRatedMovie().collectLatest { itMovies ->
            _topRateMovie.value = itMovies
        }
    }

    ////////////////////////////Popular Movie//////////////////////////

    private val _popularMovie = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovie : StateFlow<List<Movie>> = _popularMovie

    fun getPopularMovie() = viewModelScope.launch {
        homeRepository.getPopularMovie().collectLatest {itMovies ->
            _popularMovie.value = itMovies
        }
    }

    ///////////////////////////up coming//////////////////////////////////
    private val _upcomingMovie = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovie : StateFlow<List<Movie>> = _upcomingMovie

    fun getUpcomingMovie() = viewModelScope.launch {
        homeRepository.getUpcomingMovie().collectLatest {itMovies ->
            _upcomingMovie.value = itMovies
        }
    }

}