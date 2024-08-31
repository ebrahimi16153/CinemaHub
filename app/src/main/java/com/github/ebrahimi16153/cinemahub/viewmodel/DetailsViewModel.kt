package com.github.ebrahimi16153.cinemahub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ebrahimi16153.cinemahub.data.model.ImageCollection
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
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

//    private val _movie = MutableStateFlow<MovieDetail?>(null)
//    val movie: StateFlow<MovieDetail?> = _movie
//
//    fun getMovieDetails(movieID: Int) = viewModelScope.launch {
//        detailsRepository.getMovieDetailsByMovieID(movieID).collectLatest { itDetail ->
//            _movie.value = itDetail
//        }
//    }
//
//
//    private val _posters = MutableStateFlow<Wrapper<List<ImageCollection.Poster>>>(Wrapper.Idle)
//    val posters: StateFlow<Wrapper<List<ImageCollection.Poster>>> = _posters
//
//
//    fun getCollectionImages(collectionID: Int) = viewModelScope.launch(Dispatchers.IO) {
//        _posters.value = Wrapper.Loading
//
//        try {
//            detailsRepository.getCollectionImages(collectionID)
//                .collect{ itCollection ->
//
//                    if (itCollection != null && itCollection.posters.isNotEmpty()) {
//                        _posters.value = Wrapper.Success(data = itCollection.posters)
//                    }else{
//                        _posters.value = Wrapper.Error("No Data Found")
//                    }
//
//                }
//
//        } catch (e: Exception) {
//            _posters.value = Wrapper.Error(e.message.toString())
//        }
//
//    }


}