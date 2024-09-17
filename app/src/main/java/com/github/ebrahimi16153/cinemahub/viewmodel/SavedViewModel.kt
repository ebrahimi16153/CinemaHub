package com.github.ebrahimi16153.cinemahub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.repository.SaveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedViewModel @Inject constructor(private val saveRepository: SaveRepository) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieDetail>>(emptyList())
    val movies : StateFlow<List<MovieDetail>> = _movies
    fun getAllMovies() = viewModelScope.launch {
        saveRepository.getAllSavedMovies().collect{ itMovies ->
            _movies.value = itMovies
        }
    }


}