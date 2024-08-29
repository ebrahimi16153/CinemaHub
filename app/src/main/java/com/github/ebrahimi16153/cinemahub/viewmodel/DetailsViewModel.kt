package com.github.ebrahimi16153.cinemahub.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ebrahimi16153.cinemahub.data.model.ImageCollection
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.repository.DetailsRepository
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

    private val _movie = MutableStateFlow<MovieDetail?>(null)
    val movie: StateFlow<MovieDetail?> = _movie

    fun getMovieDetails(movieID: Int) = viewModelScope.launch {
        detailsRepository.getMovieDetailsByMovieID(movieID).collectLatest { itDetail ->
            _movie.value = itDetail
        }
    }


    private val _posters = MutableStateFlow<List<ImageCollection.Poster>>(emptyList())
    val posters: StateFlow<List<ImageCollection.Poster>> = _posters



    fun getCollectionImages(collectionID: Int) = viewModelScope.launch {
        detailsRepository.getCollectionImages(collectionID)
            .collectLatest { itCollection ->

                if (itCollection != null) {
                    _posters.value = itCollection.posters
                    return@collectLatest
                }
                _posters.value = emptyList()

            }
    }


}