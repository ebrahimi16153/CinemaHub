package com.github.ebrahimi16153.cinemahub.data.repository

import com.github.ebrahimi16153.cinemahub.data.model.ImageCollection
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val apiServices: ApiServices) {

    suspend fun getMovieDetailsByMovieID(movieID: Int) =
        flow<MovieDetail?> {
            if (apiServices.getMovieDetailByID(movieID).isSuccessful
                && apiServices.getMovieDetailByID(movieID).body() != null) {
                emit(apiServices.getMovieDetailByID(movieID).body())
            } else {
                emit(null)

            }
        }


    suspend fun getCollectionImages(collectionID: Int) =
        flow<ImageCollection?> {
            if(apiServices.getCollectionImages(collectionID).isSuccessful && apiServices.getCollectionImages(collectionID).body() != null){
                emit(apiServices.getCollectionImages(collectionID).body()!!)
            }else{
                emit(null)
            }
        }


}