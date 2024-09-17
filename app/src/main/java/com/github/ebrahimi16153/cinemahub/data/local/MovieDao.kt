package com.github.ebrahimi16153.cinemahub.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail
import com.github.ebrahimi16153.cinemahub.utils.MOVIE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDetail)

    @Update
    suspend fun updateMovie(movie: MovieDetail)

    @Delete
    suspend fun deleteMovie(movie: MovieDetail)

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun deleteAllMovie()

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun showAllMovie(): Flow<List<MovieDetail>>

    @Query("SELECT EXISTS (SELECT 1 FROM MOVIE_TABLE WHERE id =:movieID )")
    fun existsMovie(movieID: Int): Flow<Boolean>

}