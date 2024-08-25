package com.github.ebrahimi16153.cinemahub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.data.repository.DiscoverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val discoverRepository: DiscoverRepository):ViewModel() {

    ///////////////////////////////Genres////////////////////////////
    init {
        getGenres()
    }
    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> = _genres


    private fun getGenres() = viewModelScope.launch {
        discoverRepository.getGenresList().collectLatest { itGenres ->
            _genres.value = itGenres
        }
    }



}