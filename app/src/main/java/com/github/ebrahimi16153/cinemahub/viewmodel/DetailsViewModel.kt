package com.github.ebrahimi16153.cinemahub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ebrahimi16153.cinemahub.data.model.Credits
import com.github.ebrahimi16153.cinemahub.data.model.ImageCollection
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.model.MovieImages
import com.github.ebrahimi16153.cinemahub.data.model.Trailers
import com.github.ebrahimi16153.cinemahub.data.repository.DetailsRepository
import com.github.ebrahimi16153.cinemahub.data.wrapper.Wrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository) :
    ViewModel() {

        //////////////////////////////////API/////////////////////////////////////////////////

    ////////////////////////////////////////error////////////////////////////////////////////////

    private val _error = MutableStateFlow<Wrapper<String>>(Wrapper.Loading)
    val error: StateFlow<Wrapper<String>> = _error

    /////////////////////////////////////MovieDetail//////////////////////////////////////////////

    private val _movieDetail = MutableStateFlow<Wrapper<MovieDetail>>(Wrapper.Idle)
    val movieDetail: StateFlow<Wrapper<MovieDetail>> = _movieDetail
    fun getMovieDetail(movieID: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _movieDetail.value = Wrapper.Loading
            detailsRepository.getMovieDetailsByMovieID(movieID).collectLatest {
                _movieDetail.value = Wrapper.Success(data = it)
                _error.value = Wrapper.Idle
            }
        } catch (e: Exception) {
            _movieDetail.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }
    }


    /////////////////////////////////////MovieImages//////////////////////////////////////////////

    private val _movieImages = MutableStateFlow<Wrapper<List<MovieImages.Poster>>>(Wrapper.Idle)
    val movieImages: StateFlow<Wrapper<List<MovieImages.Poster>>> = _movieImages
    fun getMovieImages(movieID: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _movieImages.value = Wrapper.Loading
            detailsRepository.getMovieImages(movieID).collectLatest {
                if (it != null) {
                    _movieImages.value = Wrapper.Success(data = it.posters)
                } else {
                    emptyList<MovieImages.Poster>()
                }
                _error.value = Wrapper.Idle
            }
        } catch (e: Exception) {
            _movieImages.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }
    }


    //////////////////////////////////////MovieTrailer/////////////////////////////////////////////

    private val _trailers = MutableStateFlow<Wrapper<List<Trailers.Trailer>>>(Wrapper.Loading)
    val trailers: StateFlow<Wrapper<List<Trailers.Trailer>>> = _trailers
    fun getMovieTrailers(movieID: Int) = viewModelScope.launch {
        try {

            _trailers.value = Wrapper.Loading
            detailsRepository.getMovieTrailers(movieID).collectLatest {
                _trailers.value = Wrapper.Success(data = it)
                _error.value = Wrapper.Idle

            }
        } catch (e: Exception) {
            _trailers.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }
    }

    ////////////////////////////////MovieCredits//////////////////////////////////////////////////
    private val _credits = MutableStateFlow<Wrapper<Credits>>(Wrapper.Loading)
    val credits: StateFlow<Wrapper<Credits>> = _credits
    fun getMovieCredits(movieID: Int) = viewModelScope.launch {
        try {
            _credits.value = Wrapper.Loading
            detailsRepository.getCredits(movieID).collectLatest {
                _credits.value = Wrapper.Success(data = it)
                _error.value = Wrapper.Idle
            }
            } catch (e: Exception) {
            _credits.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }
    }


    ////////////////////////////////////////DATABASE//////////////////////////////////////////////

    //////////////////////////////////////////movie exist?//////////////////////////////////////

    private val _isMovieExist = MutableStateFlow<Boolean>(false)
    val isMovieExist :StateFlow<Boolean> = _isMovieExist

    fun getIsMovieExist(movieID:Int) = viewModelScope.launch {
        detailsRepository.movieEXIST(movieID).collect{ itBoolean ->
            _isMovieExist.value = itBoolean
        }
    }

    //////////////////////////////////////////insert Movie////////////////////////////////////////

    fun saveMovie(movie:MovieDetail) = viewModelScope.launch {
        detailsRepository.saveMovie(movie)
    }

    ////////////////////////////////////////deleteMovie///////////////////////////////////////////
    fun deleteMovie(movie: MovieDetail) = viewModelScope.launch {
        detailsRepository.deleteMovie(movie)
    }




}