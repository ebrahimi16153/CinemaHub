package com.github.ebrahimi16153.cinemahub.viewmodel

import androidx.lifecycle.ViewModel
import com.github.ebrahimi16153.cinemahub.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository):ViewModel() {
}