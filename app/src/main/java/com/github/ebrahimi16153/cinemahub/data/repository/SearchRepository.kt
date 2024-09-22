package com.github.ebrahimi16153.cinemahub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.paging.SearchPaging
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiServices: ApiServices) {

          fun getSearchResponse(query:String):Flow<PagingData<Movie>>{

              return Pager(
                  config = PagingConfig(pageSize = 20),
                  pagingSourceFactory = {SearchPaging(apiServices = apiServices, searchQuery = query)}
              ).flow

         }

}