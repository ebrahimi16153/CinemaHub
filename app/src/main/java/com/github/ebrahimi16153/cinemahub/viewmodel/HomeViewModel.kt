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


}