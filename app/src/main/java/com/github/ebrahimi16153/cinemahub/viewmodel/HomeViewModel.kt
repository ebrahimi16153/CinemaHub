package com.github.ebrahimi16153.cinemahub.viewmodel

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


    init {
        //get movies
        getNowPlayingMovies()
        getTopRateMovie()
        getPopularMovie()
        getUpcomingMovie()
        getMovieOFBanner()
        getGenres()

    }


    ///////////////////////////////Genres////////////////////////////
    private val _genres = MutableStateFlow<Wrapper<List<Genre>>>(Wrapper.Loading)
    val genres: StateFlow<Wrapper<List<Genre>>> = _genres


    private fun getGenres() = viewModelScope.launch {
        homeRepository.getGenresList().catch { itException ->

            _genres.value = Wrapper.Error(message = itException.message.toString())

        }.collectLatest { itGenres ->
            _genres.value = Wrapper.Success(data = itGenres)
        }
    }


    ///////////////////////////////Movies OF BANNER////////////////////////////
    private val _movieOfBanner = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val movieOfBanner: StateFlow<Wrapper<List<Movie>>> = _movieOfBanner


    private fun getMovieOFBanner() = viewModelScope.launch {
        homeRepository.trendingMovie().catch { itException ->

            _movieOfBanner.value = Wrapper.Error(message = itException.message.toString())

        }.collectLatest { itMoves ->
            _movieOfBanner.value = Wrapper.Success(data = itMoves)
        }
    }

    ///////////////////////////////NOW PLAYING////////////////////////////////
    private val _nowPlayingMovie = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val nowPlayingMovie: StateFlow<Wrapper<List<Movie>>> = _nowPlayingMovie

    private fun getNowPlayingMovies() = viewModelScope.launch {
        homeRepository.nowPlayingMovie().catch { itException ->

            _nowPlayingMovie.value = Wrapper.Error(message = itException.message.toString())

        }.collectLatest { itMovies ->
            _nowPlayingMovie.value = Wrapper.Success(data = itMovies)
        }
    }

    ////////////////////////////////////TOP RATE MOVIE/////////////////////////////////

    private val _topRateMovie = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val topRateMovie : StateFlow<Wrapper<List<Movie>>> = _topRateMovie

    private fun getTopRateMovie() = viewModelScope.launch {

        homeRepository.getTopRatedMovie().catch { itException ->

            _topRateMovie.value = Wrapper.Error(message =  itException.message.toString())

        }.collectLatest { itMovies ->
            _topRateMovie.value = Wrapper.Success(data = itMovies)
        }
    }

    ////////////////////////////Popular Movie//////////////////////////

    private val _popularMovie = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val popularMovie : StateFlow<Wrapper<List<Movie>>> = _popularMovie

    private fun getPopularMovie() = viewModelScope.launch {
        homeRepository.getPopularMovie().catch { itException ->

            _popularMovie.value = Wrapper.Error(message = itException.message.toString())

        }.collectLatest {itMovies ->
            _popularMovie.value = Wrapper.Success(data = itMovies)
        }
    }

    ///////////////////////////up coming//////////////////////////////////
    private val _upcomingMovie = MutableStateFlow<Wrapper<List<Movie>>>(Wrapper.Loading)
    val upcomingMovie : StateFlow<Wrapper<List<Movie>>> = _upcomingMovie

    private fun getUpcomingMovie() = viewModelScope.launch {
        homeRepository.getUpcomingMovie()
            .catch { itException ->

                _upcomingMovie.value = Wrapper.Error(message = itException.message.toString())

            }
            .collectLatest {itMovies ->
            _upcomingMovie.value = Wrapper.Success(data = itMovies)
        }
    }

}