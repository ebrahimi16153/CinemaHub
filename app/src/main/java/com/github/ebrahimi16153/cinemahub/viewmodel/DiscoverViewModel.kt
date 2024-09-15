package com.github.ebrahimi16153.cinemahub.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.repository.DiscoverRepository
import com.github.ebrahimi16153.cinemahub.data.wrapper.Wrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val discoverRepository: DiscoverRepository):ViewModel() {

    ///////////////////////////////Genres////////////////////////////
    init {
        getGenres()
    }

    ///////////////////////isGrid//////////////////////////////////////////////////////////////////
    val isGrid : MutableState<Boolean> = mutableStateOf(true)
    fun setIsGrid(value:Boolean) = viewModelScope.launch {
        isGrid.value = value
    }


    //////////////////////Error////////////////////////////////////////////////////////////////////
    private val _error  = MutableStateFlow<Wrapper<String>>(Wrapper.Idle)
    val error : StateFlow<Wrapper<String>> = _error

    /////////////////////Genre/////////////////////////////////////////////////////////////////////
    private val _genres = MutableStateFlow<Wrapper<List<Genre>>>(Wrapper.Loading)
    val genres: StateFlow<Wrapper<List<Genre>>> = _genres


    private fun getGenres() = viewModelScope.launch {
        try {
            discoverRepository.getGenresList().collectLatest { itGenres ->
                _error.value = Wrapper.Idle
                _genres.value = Wrapper.Success(data = itGenres)
            }
        }catch (e:Exception){
            _genres.value = Wrapper.Idle
            _error.value = Wrapper.Error(message = e.message.toString())
        }

    }

    //////////////////////////MovesByGenre////////////////////////////

    private val _moviesByGenre = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val moviesByGenre: StateFlow<PagingData<Movie>> = _moviesByGenre

    fun getMoviesByGenre(genreName:String) = viewModelScope.launch {
        discoverRepository.getMoviesByGenre(genreName).cachedIn(viewModelScope).collectLatest { itMovies ->
            _moviesByGenre.value =  itMovies

        }

    }



}