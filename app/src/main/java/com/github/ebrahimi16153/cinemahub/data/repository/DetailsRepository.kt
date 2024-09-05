package com.github.ebrahimi16153.cinemahub.data.repository

import com.github.ebrahimi16153.cinemahub.data.model.ImageCollection
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.model.MovieImages
import com.github.ebrahimi16153.cinemahub.data.model.Trailers
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val apiServices: ApiServices) {

    suspend fun getMovieDetailsByMovieID(movieID: Int) =
        flow<MovieDetail?> {
            if (apiServices.getMovieDetailByID(movieID).isSuccessful
                && apiServices.getMovieDetailByID(movieID).body() != null
            ) {
                emit(apiServices.getMovieDetailByID(movieID).body())
            } else {
                emit(null)

            }
        }

    suspend fun getMovieImages(movieID: Int) =
        flow {

            if (apiServices.getMovieImages(movieID).isSuccessful && apiServices.getMovieImages(movieID).body() != null){
                emit(apiServices.getMovieImages(movieID).body()!!)
            }else{
                emit(null)
            }

        }


    suspend fun getMovieTrailers(movieID: Int) =
        flow {

            if (apiServices.getTrailersByMovieID(movieID).isSuccessful && apiServices.getTrailersByMovieID(movieID).body() != null){
                emit(apiServices.getTrailersByMovieID(movieID).body()!!)
            }else{
                emit(null)
            }

        }


}