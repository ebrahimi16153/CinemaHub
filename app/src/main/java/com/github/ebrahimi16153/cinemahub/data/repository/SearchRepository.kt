package com.github.ebrahimi16153.cinemahub.data.repository

import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiServices: ApiServices) {

         suspend fun getSearchResponse(query:String):Flow<List<Movie>>{
             val response = apiServices.searchMovie(searchQuery = query)

             return  if (response.isSuccessful){
                 flow { response.body()?.results?.let { emit(it) } }
             }else{
                 flow { emptyList<List<Movie>>() }
             }

         }

}