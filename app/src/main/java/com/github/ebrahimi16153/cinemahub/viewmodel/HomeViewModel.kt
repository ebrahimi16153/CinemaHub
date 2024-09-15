package com.github.ebrahimi16153.cinemahub.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.repository.HomeRepository
import com.github.ebrahimi16153.cinemahub.data.wrapper.Wrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    //////////////////////////Error///////////////////////////////////
    private val _error = MutableStateFlow<Wrapper<String>>(Wrapper.Idle)
    val error: StateFlow<Wrapper<String>> = _error

    ///////////////////////////////Genres////////////////////////////
    private val _genres = MutableStateFlow<Wrapper<List<Genre>>>(Wrapper.Loading)
    val genres: StateFlow<Wrapper<List<Genre>>> = _genres


    fun getGenres() = viewModelScope.launch {
        _genres.value = Wrapper.Loading
        try {
            homeRepository.getGenresList().collectLatest { itGenres ->
                _genres.value = Wrapper.Success(data = itGenres)
            }

        } catch (e: Exception) {
            _genres.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }

    }


    ///////////////////////////////Movies OF BANNER////////////////////////////
    private val _movieOfBanner = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val movieOfBanner: StateFlow<Wrapper<List<Movie>>> = _movieOfBanner


    fun getMovieOFBanner() = viewModelScope.launch {
        _movieOfBanner.value = Wrapper.Loading
        try {
            homeRepository.trendingMovie().collectLatest { itMovies ->
                _movieOfBanner.value = Wrapper.Success(data = itMovies)
                _error.value = Wrapper.Idle
            }

        } catch (e: Exception) {
            _movieOfBanner.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }
    }

    ///////////////////////////////NOW PLAYING////////////////////////////////
    private val _nowPlayingMovie = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val nowPlayingMovie: StateFlow<Wrapper<List<Movie>>> = _nowPlayingMovie

    fun getNowPlayingMovies() = viewModelScope.launch {
        _nowPlayingMovie.value = Wrapper.Loading

        try {
            _nowPlayingMovie.value = Wrapper.Loading
            homeRepository.nowPlayingMovie().collectLatest { itMovies ->
                _nowPlayingMovie.value = Wrapper.Success(data = itMovies)
                _error.value = Wrapper.Idle
            }

        } catch (e: Exception) {
            _nowPlayingMovie.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message ?: "unknown error")
        }

    }

    ////////////////////////////////////TOP RATE MOVIE/////////////////////////////////

    private val _topRateMovie = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val topRateMovie: StateFlow<Wrapper<List<Movie>>> = _topRateMovie

    fun getTopRateMovie() = viewModelScope.launch {
        _topRateMovie.value = Wrapper.Loading

        try {
            homeRepository.getTopRatedMovie().collectLatest { itMovies ->
                _topRateMovie.value = Wrapper.Success(data = itMovies)
                _error.value = Wrapper.Idle
            }
        } catch (e: Exception) {
            _topRateMovie.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }


    }

    ////////////////////////////Popular Movie//////////////////////////

    private val _popularMovie = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val popularMovie: StateFlow<Wrapper<List<Movie>>> = _popularMovie

    fun getPopularMovie() = viewModelScope.launch {
        _popularMovie.value = Wrapper.Loading

        try {
            homeRepository.getPopularMovie().collectLatest { itMovies ->
                _popularMovie.value = Wrapper.Success(data = itMovies)
                _error.value = Wrapper.Idle
            }
        } catch (e: Exception) {
            _popularMovie.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }

    }

    ///////////////////////////up coming//////////////////////////////////
    private val _upcomingMovie = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val upcomingMovie: StateFlow<Wrapper<List<Movie>>> = _upcomingMovie

    fun getUpcomingMovie() = viewModelScope.launch {
        _upcomingMovie.value = Wrapper.Loading

        try {
            homeRepository.getUpcomingMovie().collectLatest { itMovies ->
                _upcomingMovie.value = Wrapper.Success(data = itMovies)
                _error.value = Wrapper.Idle
            }
        } catch (e: Exception) {
            _upcomingMovie.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }

    }

}