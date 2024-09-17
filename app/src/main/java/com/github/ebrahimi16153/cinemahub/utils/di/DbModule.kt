package com.github.ebrahimi16153.cinemahub.utils.di

import android.content.Context
import androidx.room.Room
import com.github.ebrahimi16153.cinemahub.data.local.MovieDataBase
import com.github.ebrahimi16153.cinemahub.utils.APP_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDataBase::class.java, APP_DATABASE)
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun providesMovieDAO(dataBase: MovieDataBase) = dataBase.movieDao


}