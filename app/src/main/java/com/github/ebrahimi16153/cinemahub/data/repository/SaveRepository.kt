package com.github.ebrahimi16153.cinemahub.data.repository

import com.github.ebrahimi16153.cinemahub.data.local.MovieDao
import javax.inject.Inject

class SaveRepository @Inject constructor(private val movieDao: MovieDao) {

    fun getAllSavedMovies() = movieDao.showAllMovie()

}