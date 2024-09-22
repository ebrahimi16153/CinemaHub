package com.github.ebrahimi16153.cinemahub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.ebrahimi16153.cinemahub.data.model.Genre
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.paging.DiscoverPaging
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverRepository @Inject constructor(private val apiServices: ApiServices){


    // genreList
    suspend fun getGenresList(): Flow<List<Genre>> {

        try {
            val response = apiServices.getGenres()
            when(response.code()){
                in 200..299 -> {
                    return flow { response.body()?.genres?.let { emit(it) } }
                }

                in 300..399 -> {
                    throw Exception("Error code 300")
                }

                in 400..499 ->{
                    throw Exception("Error Code 400")
                }

                in 500..599 -> {
                    throw Exception("Error code 500")
                }
                else -> {
                    throw Exception("unKnown Error")
                }
            }
        }catch (e:Exception){
            throw e
        }
    }



    fun getMoviesByGenre(genreName:String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {DiscoverPaging(apiServices,genreName)}
        ).flow
    }


}