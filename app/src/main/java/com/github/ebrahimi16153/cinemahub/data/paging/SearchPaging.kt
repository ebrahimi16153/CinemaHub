package com.github.ebrahimi16153.cinemahub.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.data.server.ApiServices

class SearchPaging(private val apiServices: ApiServices, private val searchQuery: String):
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {return null}

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
       return try {
           val page = params.key?: 1
           val response = apiServices.searchMovie(searchQuery = searchQuery,page = page)
           val data = response.body()?.results?: emptyList()
           return LoadResult.Page(
               data = data,
               prevKey = if (page == 1) null else page -1,
               nextKey = if(!response.isSuccessful || data.isEmpty()) null else page+1
           )
       }catch (e:Exception){
           LoadResult.Error(e)
       }
    }
}