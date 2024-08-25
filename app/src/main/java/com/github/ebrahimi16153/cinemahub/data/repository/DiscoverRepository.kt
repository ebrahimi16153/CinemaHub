package com.github.ebrahimi16153.cinemahub.data.repository

import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverRepository @Inject constructor(private val apiServices: ApiServices){

    suspend fun getGenresList(): Flow<List<Genre>> {
        val response = apiServices.getGenres()
        return if (response.isSuccessful){
            flow {
                response.body()?.let { emit(it.genres) }
            }
        }else{
            flow { emptyList<List<Genre>>() }
        }
    }
}