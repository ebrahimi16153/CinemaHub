package com.github.ebrahimi16153.cinemahub.data.repository

import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiServices: ApiServices) {

    suspend fun trendingMovie(): Flow<List<Movie>> {
        try {
            val response = apiServices.getTrendingMovie()

            when(response.code()){
                200 -> { return flow { emit(response.body()?.results?: emptyList()) }}
                in 300..399 -> {throw Exception("Error code 300")}
                in 400..499 -> {throw Exception("Error code 400")}
                in 500..599 -> {throw Exception("Error code 500")}
                else -> {throw Exception("Unknown Error")}
            }
        }catch (e:Exception){
            throw e
        }
    }


    suspend fun nowPlayingMovie(): Flow<List<Movie>> {
        try {
            val response = apiServices.getNowPlayingMovies()
            when(response.code()){
                200 -> { return flow { emit(response.body()?.results?: emptyList()) }}
                in 300..399 -> {throw Exception("Error code 300")}
                in 400..499 -> {throw Exception("Error code 400")}
                in 500..599 -> {throw Exception("Error code 500")}
                else -> {throw Exception("Unknown Error")}
            }

        }catch (e:Exception){
            throw e
        }
    }


    suspend fun getTopRatedMovie():Flow<List<Movie>>{

        try {
            val response = apiServices.getTopRatedMovie()
            when(response.code()){
                200 -> { return flow { emit(response.body()?.results?: emptyList()) }}
                in 300..399 -> {throw Exception("Error code 300")}
                in 400..499 -> {throw Exception("Error code 400")}
                in 500..599 -> {throw Exception("Error code 500")}
                else -> {throw Exception("Unknown Error")}
            }
        }catch (e:Exception){
            throw e
        }
    }



    suspend fun getPopularMovie():Flow<List<Movie>>{

        try {
            val response = apiServices.getPopularMovie()
            when(response.code()){
                200 -> { return flow { emit(response.body()?.results?: emptyList()) }}
                in 300..399 -> {throw Exception("Error code 300")}
                in 400..499 -> {throw Exception("Error code 400")}
                in 500..599 -> {throw Exception("Error code 500")}
                else -> {throw Exception("Unknown Error")}
            }

        }catch (e:Exception){
          throw e
        }
    }




    suspend fun getUpcomingMovie():Flow<List<Movie>>{

        try {
            val response = apiServices.getUpcomingMovies()
            when(response.code()){
                200 -> { return flow { emit(response.body()?.results?: emptyList()) }}
                in 300..399 -> {throw Exception("Error code 300")}
                in 400..499 -> {throw Exception("Error code 400")}
                in 500..599 -> {throw Exception("Error code 500")}
                else -> {throw Exception("Unknown Error")}
            }

        }catch (e:Exception){
            throw e
        }
    }


    suspend fun getGenresList():Flow<List<Genre>>{
        try {
            val response = apiServices.getGenres()
            when(response.code()){
                200 -> { return flow { emit(response.body()?.genres?: emptyList())}}
                in 300..399 -> {throw Exception("Error code 300")}
                in 400..499 -> {throw Exception("Error code 400")}
                in 500..599 -> {throw Exception("Error code 500")}
                else -> {throw Exception("Unknown Error")}
            }
        }catch (e:Exception){
            throw e
        }
    }
}