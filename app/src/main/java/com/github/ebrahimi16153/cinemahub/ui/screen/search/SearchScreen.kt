package com.github.ebrahimi16153.cinemahub.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.ebrahimi16153.cinemahub.R
import com.github.ebrahimi16153.cinemahub.data.model.Movie
import com.github.ebrahimi16153.cinemahub.ui.componnet.GridMovieItems
import com.github.ebrahimi16153.cinemahub.utils.Route
import com.github.ebrahimi16153.cinemahub.viewmodel.SearchViewModel

@Composable
fun SearchScreen(navHostController: NavHostController, searchViewModel: SearchViewModel) {


    val searchQuery by searchViewModel.searchQuery

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
           // searchNar
            MySearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = {
                    searchViewModel.setSearchQuery(it)
                    searchViewModel.getResponseOfSearch(it)


                })

           //MovieList
          GridMovieList(searchViewModel = searchViewModel, onMovieClick = {navHostController.navigate(Route.Details.name+"/$it")})




    }


}


@Preview
@Composable
fun MySearchBar(
    searchQuery: String = "SearchBar",
    onSearchQueryChange: (String) -> Unit = {}
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        maxLines = 1,
        label = { Text(text = stringResource(id = R.string.search_label)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions.Default
    )


}



@Composable
fun GridMovieList(searchViewModel: SearchViewModel, onMovieClick:(Int) -> Unit = {}){

    val searchResponse  = searchViewModel.responseOfSearch.collectAsLazyPagingItems()

    LazyVerticalStaggeredGrid(
        columns =StaggeredGridCells.Adaptive(130.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalItemSpacing = 3.dp,
        horizontalArrangement = Arrangement.spacedBy(1.dp)

    ) {

        items(count = searchResponse.itemCount){index ->

            searchResponse[index]?.let {
                GridMovieItems(movie = it){ movieID ->
                    onMovieClick(movieID)
                }
            }


        }
        
    }


}