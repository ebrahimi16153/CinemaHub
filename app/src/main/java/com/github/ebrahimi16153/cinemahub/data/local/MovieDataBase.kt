package com.github.ebrahimi16153.cinemahub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.ebrahimi16153.cinemahub.data.model.MovieDetail


@Database(entities = [MovieDetail::class], version = 1, exportSchema = false)
abstract class MovieDataBase :RoomDatabase(){

    abstract val movieDao:MovieDao

}