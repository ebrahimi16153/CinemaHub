package com.github.ebrahimi16153.cinemahub.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {



    //////////////////////////////Search Query/////////////////////////////////
    val searchQuery: MutableState<String> = mutableStateOf("")

    private val _responseOfSearch = MutableStateFlow<List<Movie>>(emptyList())
    val responseOfSearch :StateFlow<List<Movie>> = _responseOfSearch

    fun setSearchQuery(search: String) = viewModelScope.launch {
        searchQuery.value = search
    }


    fun getResponseOfSearch(search: String) = viewModelScope.launch {
        searchRepository.getSearchResponse(search).collectLatest { itMoves ->
            _responseOfSearch.value = itMoves
        }
    }


}