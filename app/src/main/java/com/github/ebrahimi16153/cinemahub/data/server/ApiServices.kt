package com.github.ebrahimi16153.cinemahub.data.server

import com.github.ebrahimi16153.cinemahub.data.model.Credits
import com.github.ebrahimi16153.cinemahub.data.model.GenresOfMovie
import com.github.ebrahimi16153.cinemahub.data.model.ImageCollection
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.data.model.Movies
import com.github.ebrahimi16153.cinemahub.data.model.NowPlayingMovie
import com.github.ebrahimi16153.cinemahub.data.model.Trailers
import com.github.ebrahimi16153.cinemahub.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    ////////////////////////HOME Screen ///////////////////

    //  https://api.themoviedb.org/3/trending/movie/week?language=en-US&api_key=444a3900eac59a6892d47a7250a984f5
    //  tradingMovie
    @GET("trending/movie/{week}")
 suspend fun getTrendingMovie(
        @Path("week") duration: String = "week",
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY
    ):Response<Movies>


    //    https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1&api_key=444a3900eac59a6892d47a7250a984f5
    //    NowPlaying
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<NowPlayingMovie>


    //    https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1&api_key=444a3900eac59a6892d47a7250a984f5
    //    topRate
    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movies>

    //    https://api.themoviedb.org/3/movie/popular?language=en-US&page=1&api_key=444a3900eac59a6892d47a7250a984f5
    //     popular

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movies>

    //    https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1&api_key=444a3900eac59a6892d47a7250a984f5

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movies>


    //    https://api.themoviedb.org/3/genre/movie/list?language=en&api_key=444a3900eac59a6892d47a7250a984f5
    //    https://api.themoviedb.org/3/genre/movie/list?language=en&api_key=444a3900eac59a6892d47a7250a984f5

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "en",
        @Query("api_key") apiKey: String = API_KEY
    ): Response<GenresOfMovie>


//    https://api.themoviedb.org/3/search/movie?query=god&include_adult=true&language=en-US&page=1&api_key=444a3900eac59a6892d47a7250a984f5
    @GET("search/movie")
    suspend fun searchMovie(
    @Query("query") searchQuery:String,
    @Query("page") page: Int,
    @Query("include_adult") adult:Boolean = false,
    @Query("language") language: String = "en",
    @Query("api_key") apiKey: String = API_KEY
    ):Response<Movies>


    ///////////////////////// DISCOVER  ///////////////////////////////////////////////////


    //    https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&api_key=444a3900eac59a6892d47a7250a984f5
    @GET("discover/movie")
    suspend fun getMovieByGenres(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int ,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") withGenres: String,
        @Query("api_key") apiKey: String = API_KEY

    ): Response<Movies>


    //////////////////////Detail ////////////////////////////////////////////////////////

    //https://api.themoviedb.org/3/movie/280180?language=en-US&api_key=444a3900eac59a6892d47a7250a984f5

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailByID(
        @Path("movie_id") movieID: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY

    ): Response<MovieDetail>

    // https://api.themoviedb.org/3/collection/85861/images?&api_key=444a3900eac59a6892d47a7250a984f5
    @GET("collection/{collection_id}}/images")
    suspend fun getCollectionImages(
        @Path("collection_id") collectionID: Int,
        @Query("api_key") apiKey: String = API_KEY

    ): ImageCollection

    //    https://api.themoviedb.org/3/movie/748783/credits?language=en-US&api_key=444a3900eac59a6892d47a7250a984f5
    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsByMovieID(
        @Path("movie_id") movieID: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY
    ): Credits

    //    https://api.themoviedb.org/3/movie/533535/videos?language=en-US&api_key=444a3900eac59a6892d47a7250a984f5
    @GET("movie/{movie_id}/videos")
    suspend fun getTrailersByMovieID(
        @Path("movie_id") movieID: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY
    ): Trailers


}