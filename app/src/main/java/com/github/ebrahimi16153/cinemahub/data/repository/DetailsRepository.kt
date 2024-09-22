package com.github.ebrahimi16153.cinemahub.data.repository

import com.github.ebrahimi16153.cinemahub.data.local.MovieDao
import com.github.ebrahimi16153.cinemahub.data.model.Credits
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.model.MovieImages
import com.github.ebrahimi16153.cinemahub.data.model.Trailers
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val movieDao: MovieDao
) {


    //////////////////////////////////////API//////////////////////////////////////////////////
    suspend fun getMovieDetailsByMovieID(movieID: Int): Flow<MovieDetail> {
        try {
            val response = apiServices.getMovieDetailByID(movieID)
            when (response.code()) {
                in 200..299 -> {
                    return if (response.body() != null)
                        flow { emit(response.body()!!) }
                    else
                        emptyFlow()
                }

                in 300..399 -> {
                    throw Exception("Error code 300")
                }

                in 400..499 -> {
                    throw Exception("Error code 400")
                }

                in 500..599 -> {
                    throw Exception("Error code 500")
                }

                else -> {
                    throw Exception("Unknown Error")

                }
            }

        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getMovieImages(movieID: Int): Flow<MovieImages?> {

        try {
            val response = apiServices.getMovieImages(movieID)
            when (response.code()) {
                200 -> {
                    return if (response.body() != null)
                        flow { emit(response.body()!!) }
                    else
                        emptyFlow()
                }

                in 300..399 -> {
                    throw Exception("Error code 300")
                }

                in 400..499 -> {
                    throw Exception("Error code 400")
                }

                in 500..599 -> {
                    throw Exception("Error code 500")
                }

                else -> {
                    throw Exception()
                }
            }
        } catch (e: Exception) {
            throw e
        }

    }


    suspend fun getMovieTrailers(movieID: Int): Flow<List<Trailers.Trailer>> {
        try {
            val response = apiServices.getTrailersByMovieID(movieID)
            when (response.code()) {
                200 -> {
                    return if (response.body() != null)
                        flow { emit(response.body()!!.results) }
                    else
                        emptyFlow()
                }

                in 300..399 -> {
                    throw Exception("Error code 300")
                }

                in 400..499 -> {
                    throw Exception("Error code 400")
                }

                in 500..599 -> {
                    throw Exception("Error code 500")
                }

                else -> {
                    throw Exception("Unknown Error")
                }
            }

        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getCredits(movieID: Int): Flow<Credits> {

        try {
            val response = apiServices.getCreditsByMovieID(movieID)
            when (response.code()) {
                200 -> {
                    return if (response.body() != null)
                        flow { emit(response.body()!!) }
                    else
                        emptyFlow()
                }

                in 300..399 -> {
                    throw Exception("Error code 300")
                }

                in 400..499 -> {
                    throw Exception("Error code 400")
                }

                in 500..599 -> {
                    throw Exception("Error code 500")
                }

                else -> {
                    throw Exception("Unknown Error")
                }
            }

        } catch (e: Exception) {
            throw e
        }
    }

    ///////////////////////////////////DATABASE//////////////////////////////////////////////////
    fun movieEXIST(movieID: Int) = movieDao.existsMovie(movieID)

    suspend fun saveMovie(movie: MovieDetail) = movieDao.insertMovie(movie)

    suspend fun deleteMovie(movie: MovieDetail) = movieDao.deleteMovie(movie)
}