package com.github.ebrahimi16153.cinemahub.data.repository

import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.data.model.GenresOfMovie
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.time.Duration

class HomeRepository @Inject constructor(private val apiServices: ApiServices) {

    suspend fun trendingMovie(): Flow<List<Movie>> {
        val response = apiServices.getTrendingMovie()
        if (response.isSuccessful) {
            return flow { response.body()?.let { emit(it.results) } }
        } else {
            return flow { emptyList<List<Movie>>() }
        }
    }


    suspend fun nowPlayingMovie(): Flow<List<Movie>> {
        val response = apiServices.getNowPlayingMovies()
        return if (response.isSuccessful) {
            flow { response.body()?.let { emit(it.results) } }
        } else {
            flow { emptyList<List<Movie>>() }
        }

    }


    suspend fun getTopRatedMovie():Flow<List<Movie>>{

        val response = apiServices.getTopRatedMovie()
        return if (response.isSuccessful){
            flow { response.body()?.let { emit(it.results) } }
        }else{
            flow { emptyList<List<Movie>>() }
        }
    }



    suspend fun getPopularMovie():Flow<List<Movie>>{

        val response = apiServices.getPopularMovie()
        return if (response.isSuccessful){
            flow { response.body()?.let { emit(it.results) } }
        }else{
            flow { emptyList<List<Movie>>() }
        }

    }




    suspend fun getUpcomingMovie():Flow<List<Movie>>{

        val response = apiServices.getUpcomingMovies()
        return if (response.isSuccessful){
            flow { response.body()?.let { emit(it.results) } }
        }else{
            flow { emptyList<List<Movie>>() }
        }
    }

    suspend fun getGenresList():Flow<List<Genre>>{
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