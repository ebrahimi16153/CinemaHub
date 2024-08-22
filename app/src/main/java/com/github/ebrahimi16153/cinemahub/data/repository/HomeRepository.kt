package com.github.ebrahimi16153.cinemahub.data.repository

import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.time.Duration

class HomeRepository @Inject constructor(private val apiServices: ApiServices)  {

       suspend fun trendingMovie() :Flow<List<Movie>>{
               val response = apiServices.getTrendingMovie()
               if (response.isSuccessful){
                       return flow { response.body()?.let { emit(it.results) } }
               }else{
                       return flow { emptyList<List<Movie>>() }
               }
       }

}